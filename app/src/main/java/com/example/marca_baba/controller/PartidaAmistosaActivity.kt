package com.example.marca_baba.controller

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.marca_baba.model.Partida
import com.example.marca_baba.R
import com.example.marca_baba.dao.CampoDAO
import com.example.marca_baba.data.AppDatabase
import com.example.marca_baba.data.DadosPartida
import com.example.marca_baba.view.TimeViewModel
import com.example.marca_baba.view.TimeViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class PartidaAmistosaActivity : AppCompatActivity() {

    private var dataSelecionada: String? = null
    private var horaSelecionada: String? = null

    private lateinit var timeViewModel: TimeViewModel

    private lateinit var campoDAO: CampoDAO

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

        // Inicializa o ViewModel para acessar os dados do banco de dados
        timeViewModel = ViewModelProvider(
            this,
            TimeViewModelFactory(AppDatabase.getDatabase(this).timeDAO())  // Passa o timeDAO
        ).get(TimeViewModel::class.java)

        // Inicializa o DAO para acessar os dados do banco de dados
        campoDAO = AppDatabase.getDatabase(this).campoDAO()

//        val listaTimes = DadosPartida.listaTimes.map { it.getNomeTime() }
//        val listaCampos = DadosPartida.listaCampos.map { "${it.getRua()}, ${it.getBairro()}, ${it.getCidade()}" }

        // Buscar times do banco de dados e atualize a lista no Spinner
        timeViewModel.listaTimes.observe(this) { times ->
            val listaTimes = times.map { it.nomeTime }

            val adapterTimes = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTimes)
            adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTime1.adapter = adapterTimes
            spinnerTime2.adapter = adapterTimes
        }

//        val adapterCampos = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCampos)
//        adapterCampos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerCampos.adapter = adapterCampos

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
                Toast.makeText(this, "Escolha times diferentes!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dataSelecionada == null || horaSelecionada == null) {
                Toast.makeText(this, "Escolha data e hora da partida!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se há pelo menos dois times cadastrados
            if (DadosPartida.listaTimes.size < 2) {
                Toast.makeText(this, "Cadastre pelo menos dois times antes de agendar uma partida!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val campoSelecionado = spinnerCampos.selectedItem.toString()

            val mensagem = """
                Partida amistosa iniciada!
                Time 1: $time1Selecionado
                Time 2: $time2Selecionado
                Campo: $campoSelecionado
                Data: $dataSelecionada
                Hora: $horaSelecionada
            """.trimIndent()

            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

            val partida = Partida(
                time1Selecionado,
                time2Selecionado,
                campoSelecionado,
                dataSelecionada!!,
                horaSelecionada!!
            )
            DadosPartida.listaPartidas.add(partida)

            Toast.makeText(this, "Partida agendada com sucesso!", Toast.LENGTH_LONG).show()
        }
    }
}