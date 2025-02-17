package com.example.marca_baba.controller

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.marca_baba.model.Campeonato
import com.example.marca_baba.data.DadosPartida
import com.example.marca_baba.R
import com.example.marca_baba.model.Time

class CampeonatoActivity : AppCompatActivity() {

    private lateinit var campeonatoNomeEditText: EditText
    private lateinit var timesSpinner: Spinner
    private lateinit var adicionarTimeButton: Button
    private lateinit var salvarCampeonatoButton: Button
    private lateinit var timesListView: ListView

    private val timesCadastrados = ArrayList(DadosPartida.listaTimes)
    private val campeonatosSalvos = mutableListOf<Campeonato>()
    private val timesDoCampeonato = mutableListOf<Time>()
    private lateinit var timesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campeonato)

        campeonatoNomeEditText = findViewById(R.id.campeonatoNomeEditText)
        timesSpinner = findViewById(R.id.timesSpinner)
        adicionarTimeButton = findViewById(R.id.adicionarTimeButton)
        salvarCampeonatoButton = findViewById(R.id.salvarCampeonatoButton)
        timesListView = findViewById(R.id.timesListView)

        val timesNomes = timesCadastrados.map { it.nomeTime }

        // Configurando o Spinner
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timesNomes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timesSpinner.adapter = spinnerAdapter

        // Configurando o ListView
        timesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, timesDoCampeonato.map { it.nomeTime })
        timesListView.adapter = timesAdapter

        adicionarTimeButton.setOnClickListener { adicionarTime() }
        salvarCampeonatoButton.setOnClickListener { salvarCampeonato() }
    }

    private fun adicionarTime() {
        val timeSelecionadoNome = timesSpinner.selectedItem as String
        val time = timesCadastrados.find { it.nomeTime == timeSelecionadoNome }

        if (time != null && timesDoCampeonato.size < 8 && !timesDoCampeonato.contains(time)) {
            timesDoCampeonato.add(time)
            atualizarLista()
        }
    }

    private fun salvarCampeonato() {
        val nomeCampeonato = campeonatoNomeEditText.text.toString()

        // Verifica se o nome do campeonato está vazio
        if (nomeCampeonato.isEmpty()) {
            Toast.makeText(this, "Digite o nome do campeonato!", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se há pelo menos 2 times no campeonato
        if (nomeCampeonato.isNotEmpty() && timesDoCampeonato.size >= 2) {
            val campeonato = Campeonato(
                nomeCampeonato,
                ArrayList(timesDoCampeonato)
            )
            campeonatosSalvos.add(campeonato)
            campeonatoNomeEditText.setText("")
            timesDoCampeonato.clear()
            atualizarLista()
        }

        // Verifica se já existe um campeonato com o mesmo nome
        if (DadosPartida.listaCampeonatos.any { it.nome == nomeCampeonato }) {
            Toast.makeText(this, "Já existe um campeonato com esse nome!", Toast.LENGTH_SHORT).show()
            return
        }

        // Salva o campeonato
        val campeonato = Campeonato(
            nomeCampeonato,
            ArrayList(timesDoCampeonato)
        )
        DadosPartida.listaCampeonatos.add(campeonato)

        // Limpa os campos após salvar
        campeonatoNomeEditText.text.clear()
        timesDoCampeonato.clear()
        atualizarLista()
        Toast.makeText(this, "Campeonato salvo com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun atualizarLista() {
        timesAdapter.clear()
        timesAdapter.addAll(timesDoCampeonato.map { it.nomeTime })
        timesAdapter.notifyDataSetChanged()
    }
}
