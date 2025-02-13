package com.example.marca_baba

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

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
        if (nomeCampeonato.isNotEmpty() && timesDoCampeonato.size >= 2) {
            val campeonato = Campeonato(nomeCampeonato, ArrayList(timesDoCampeonato))
            campeonatosSalvos.add(campeonato)
            campeonatoNomeEditText.setText("")
            timesDoCampeonato.clear()
            atualizarLista()
        }
    }

    private fun atualizarLista() {
        timesAdapter.clear()
        timesAdapter.addAll(timesDoCampeonato.map { it.nomeTime })
        timesAdapter.notifyDataSetChanged()
    }
}
