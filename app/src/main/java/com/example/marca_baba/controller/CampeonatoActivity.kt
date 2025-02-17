package com.example.marca_baba.controller

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.marca_baba.R
import com.example.marca_baba.dao.CampeonatoDAO
import com.example.marca_baba.dao.CampeonatoTimeDAO
import com.example.marca_baba.dao.TimeDAO
import com.example.marca_baba.data.AppDatabase
import com.example.marca_baba.data.Campeonato
import com.example.marca_baba.data.CampeonatoTime
import com.example.marca_baba.data.Time
import kotlinx.coroutines.launch

class CampeonatoActivity : AppCompatActivity() {

    private lateinit var campeonatoNomeEditText: EditText
    private lateinit var timesSpinner: Spinner
    private lateinit var adicionarTimeButton: Button
    private lateinit var salvarCampeonatoButton: Button
    private lateinit var timesListView: ListView

    private val timesCadastrados = ArrayList<Time>()
    private val timesDoCampeonato = mutableListOf<Time>()
    private lateinit var timesAdapter: ArrayAdapter<String>

    private lateinit var timeDAO: TimeDAO
    private lateinit var campeonatoDAO: CampeonatoDAO
    private lateinit var campeonatoTimeDAO: CampeonatoTimeDAO
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campeonato)

        campeonatoNomeEditText = findViewById(R.id.campeonatoNomeEditText)
        timesSpinner = findViewById(R.id.timesSpinner)
        adicionarTimeButton = findViewById(R.id.adicionarTimeButton)
        salvarCampeonatoButton = findViewById(R.id.salvarCampeonatoButton)
        timesListView = findViewById(R.id.timesListView)

        // Inicializa o banco de dados e os DAOs
        db = AppDatabase.getDatabase(this)
        timeDAO = db.timeDAO()
        campeonatoDAO = db.campeonatoDAO()
        campeonatoTimeDAO = db.campeonatoTimeDAO()

        // Configura o Spinner com os times do banco de dados
        lifecycleScope.launch {
            val times = timeDAO.listarTodosTimes()
            timesCadastrados.addAll(times)

            val timesNomes = timesCadastrados.map { it.nomeTime }
            val spinnerAdapter = ArrayAdapter(this@CampeonatoActivity, android.R.layout.simple_spinner_item, timesNomes)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            timesSpinner.adapter = spinnerAdapter
        }

        // Configura o ListView
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
        } else if (timesDoCampeonato.size >= 8) {
            Toast.makeText(this, "Máximo de 8 times atingido!", Toast.LENGTH_SHORT).show()
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
        if (timesDoCampeonato.size < 2) {
            Toast.makeText(this, "Adicione pelo menos 2 times ao campeonato!", Toast.LENGTH_SHORT).show()
            return
        }

        // Salva o campeonato no banco de dados
        lifecycleScope.launch {
            try {
                val campeonato = Campeonato(nome = nomeCampeonato)
                val campeonatoId = campeonatoDAO.inserirCampeonato(campeonato) // Retorna o ID do campeonato (Long)

                // Associa os times ao campeonato
                timesDoCampeonato.forEach { time ->
                    val campeonatoTime = CampeonatoTime(
                        campeonatoId = campeonatoId, // Já é Long
                        idTime = time.id // Já é Long
                    )
                    campeonatoTimeDAO.inserirCampeonatoTime(campeonatoTime) // Insere o relacionamento
                }

                Toast.makeText(this@CampeonatoActivity, "Campeonato salvo com sucesso!", Toast.LENGTH_SHORT).show()

                // Limpa os campos após salvar
                campeonatoNomeEditText.text.clear()
                timesDoCampeonato.clear()
                atualizarLista()
            } catch (e: Exception) {
                Toast.makeText(this@CampeonatoActivity, "Erro ao salvar o campeonato: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarLista() {
        timesAdapter.clear()
        timesAdapter.addAll(timesDoCampeonato.map { it.nomeTime })
        timesAdapter.notifyDataSetChanged()
    }
}