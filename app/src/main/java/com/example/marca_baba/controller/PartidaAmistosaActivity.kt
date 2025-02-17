package com.example.marca_baba.controller

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.marca_baba.R
import com.example.marca_baba.dao.CampoDAO
import com.example.marca_baba.dao.PartidaDAO
import com.example.marca_baba.dao.TimeDAO
import com.example.marca_baba.data.AppDatabase
import com.example.marca_baba.data.Campo
import com.example.marca_baba.data.Partida
import com.example.marca_baba.view.TimeViewModel
import com.example.marca_baba.view.TimeViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class PartidaAmistosaActivity : AppCompatActivity() {

    private var dataSelecionada: String? = null
    private var horaSelecionada: String? = null

    private lateinit var timeViewModel: TimeViewModel
    private lateinit var campoDAO: CampoDAO
    private lateinit var timeDAO: TimeDAO
    private lateinit var partidaDAO: PartidaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_amistosa)

        val spinnerTime1: Spinner = findViewById(R.id.spinnerTime1)
        val spinnerTime2: Spinner = findViewById(R.id.spinnerTime2)
        val spinnerCampos: Spinner = findViewById(R.id.spinnerCampos)
        val btnIniciarPartida: Button = findViewById(R.id.btnIniciarPartida)
        val txtDataSelecionada: TextView = findViewById(R.id.txtDataSelecionada)
        val txtHoraSelecionada: TextView = findViewById(R.id.txtHoraSelecionada)
        val btnDataSelecionada: Button = findViewById(R.id.btnSelecionarData)
        val btnEscolherHora: Button = findViewById(R.id.btnSelecionarHora)

        // Inicializa o banco de dados e os DAOs
        val db = AppDatabase.getDatabase(this)
        campoDAO = db.campoDAO()
        timeDAO = db.timeDAO()
        partidaDAO = db.partidaDAO()

        // Inicializa o ViewModel para acessar os dados do banco de dados
        timeViewModel = ViewModelProvider(
            this,
            TimeViewModelFactory(timeDAO)
        ).get(TimeViewModel::class.java)

        // Buscar times do banco de dados e atualize a lista no Spinner
        timeViewModel.listaTimes.observe(this) { times ->
            val listaTimes = times.map { it.nomeTime }

            val adapterTimes = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTimes)
            adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTime1.adapter = adapterTimes
            spinnerTime2.adapter = adapterTimes
        }

        // Buscar campos do banco de dados e atualize a lista no Spinner
        lifecycleScope.launch {
            campoDAO.listarTodosCampos().collect { campos ->
                val listaCampos = campos.map { "${it.rua}, ${it.bairro}, ${it.cidade}" }
                val adapterCampos = ArrayAdapter(this@PartidaAmistosaActivity, android.R.layout.simple_spinner_item, listaCampos)
                adapterCampos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCampos.adapter = adapterCampos
            }
        }

        // Botão para escolher a data
        btnDataSelecionada.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                dataSelecionada = "$dayOfMonth/${month + 1}/$year"
                txtDataSelecionada.text = "Data: $dataSelecionada"
            }, ano, mes, dia)
            datePicker.show()
        }

        // Botão para escolher a hora
        btnEscolherHora.setOnClickListener {
            val calendario = Calendar.getInstance()
            val hora = calendario.get(Calendar.HOUR_OF_DAY)
            val minuto = calendario.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                horaSelecionada = String.format("%02d:%02d", hourOfDay, minute)
                txtHoraSelecionada.text = "Hora: $horaSelecionada"
            }, hora, minuto, true)
            timePicker.show()
        }

        // Botão para iniciar a partida
        btnIniciarPartida.setOnClickListener {
            val time1Selecionado = spinnerTime1.selectedItem.toString()
            val time2Selecionado = spinnerTime2.selectedItem.toString()

            if (time1Selecionado == time2Selecionado) {
                runOnUiThread {
                    Toast.makeText(this@PartidaAmistosaActivity, "Escolha times diferentes!", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener
            }

            if (dataSelecionada == null || horaSelecionada == null) {
                runOnUiThread {
                    Toast.makeText(this@PartidaAmistosaActivity, "Escolha data e hora da partida!", Toast.LENGTH_SHORT).show()
                }
                return@setOnClickListener
            }

            lifecycleScope.launch {
                // Verifica se há pelo menos dois times cadastrados
                val timesCadastrados = timeDAO.listarTodosTimes()
                if (timesCadastrados.size < 2) {
                    runOnUiThread {
                        Toast.makeText(this@PartidaAmistosaActivity, "Cadastre pelo menos dois times antes de agendar uma partida!", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                // Busca os IDs dos times selecionados
                val time1 = timesCadastrados.find { it.nomeTime == time1Selecionado }
                val time2 = timesCadastrados.find { it.nomeTime == time2Selecionado }

                if (time1 == null || time2 == null) {
                    runOnUiThread {
                        Toast.makeText(this@PartidaAmistosaActivity, "Erro ao buscar times selecionados!", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                // Coleta os campos do Flow
                val campos = mutableListOf<Campo>()
                campoDAO.listarTodosCampos().collect { listaCampos ->
                    campos.addAll(listaCampos)
                }

                // Busca o campo selecionado
                val campoSelecionadoTexto = spinnerCampos.selectedItem.toString()
                val campo = campos.find { "${it.rua}, ${it.bairro}, ${it.cidade}" == campoSelecionadoTexto }

                if (campo == null) {
                    runOnUiThread {
                        Toast.makeText(this@PartidaAmistosaActivity, "Erro ao buscar campo selecionado!", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                // Salva a partida no banco de dados
                val partida = Partida(
                    id = 0, // O ID será gerado automaticamente pelo banco de dados
                    time1Id = time1.id.toInt(),
                    time2Id = time2.id.toInt(),
                    campoId = campo.id,
                    data = dataSelecionada!!,
                    hora = horaSelecionada!!
                )

                partidaDAO.inserirPartida(partida)

                runOnUiThread {
                    Toast.makeText(this@PartidaAmistosaActivity, "Partida agendada com sucesso!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}