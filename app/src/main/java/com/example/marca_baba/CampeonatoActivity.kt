package com.example.marca_baba

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CampeonatoActivity : AppCompatActivity() {

    private lateinit var campeonato: Campeonato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campeonato)

        // Referências para os componentes da tela
        val spinnerTimes: Spinner = findViewById(R.id.spinnerTimes)
        val btnAdicionarTime: Button = findViewById(R.id.btnAdicionarTime)
        val btnGerarConfrontos: Button = findViewById(R.id.btnGerarConfrontos)
        val txtConfrontos: TextView = findViewById(R.id.txtConfrontos)
        val btnSimularCampeonato: Button = findViewById(R.id.btnSimularCampeonato)
        val txtCampeao: TextView = findViewById(R.id.txtCampeao)

        // Inicializa o campeonato
        campeonato = Campeonato()

        // Preenche o spinner com os times cadastrados
        val listaTimes = DadosPartida.listaTimes.map { it.getNomeTime() }
        val adapterTimes = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTimes)
        adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimes.adapter = adapterTimes

        // Botão para adicionar time ao campeonato
        btnAdicionarTime.setOnClickListener {
            val timeSelecionado = spinnerTimes.selectedItem.toString()
            val time = DadosPartida.listaTimes.find { it.getNomeTime() == timeSelecionado }

            if (time != null) {
                campeonato.adicionarTime(time)
                Toast.makeText(this, "Time $timeSelecionado adicionado ao campeonato!", Toast.LENGTH_SHORT).show()
            }
        }

        // Botão para gerar confrontos
        btnGerarConfrontos.setOnClickListener {
            campeonato.gerarConfrontos()
            val confrontos = campeonato.getConfrontos().joinToString("\n")
            txtConfrontos.text = "Confrontos:\n$confrontos"
        }

        // Botão para simular o campeonato
        btnSimularCampeonato.setOnClickListener {
            campeonato.simularCampeonato()
            val campeao = campeonato.getCampeao()
            txtCampeao.text = "Campeão: $campeao"
            Toast.makeText(this, "Campeonato simulado! Campeão: $campeao", Toast.LENGTH_LONG).show()
        }
    }
}