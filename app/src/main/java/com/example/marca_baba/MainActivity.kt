package com.example.marca_baba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.marca_baba.controller.CadastrarCampo
import com.example.marca_baba.controller.CadastrarTime
import com.example.marca_baba.controller.CampeonatoActivity
import com.example.marca_baba.controller.EventosActivity
import com.example.marca_baba.controller.PartidaAmistosaActivity
import com.example.marca_baba.data.Campeonato
import com.example.marca_baba.data.Partida

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referências para os botões fixos
        val btnCadastrarTime: Button = findViewById(R.id.btnCadastrarTime)
        val btnCadastrarCampo: Button = findViewById(R.id.btnCadastrarCampo)
        val btnPartidaAmistosa: Button = findViewById(R.id.btnPartidaAmistosa)
        val btnCampeonato: Button = findViewById(R.id.btnCampeonato)
        val btnEventos: Button = findViewById(R.id.btnEventos)

        // Botão para Cadastrar Time
        btnCadastrarTime.setOnClickListener {
            val intent = Intent(this, CadastrarTime::class.java)
            startActivity(intent)
        }

        // Botão para Cadastrar Campo
        btnCadastrarCampo.setOnClickListener {
            val intent = Intent(this, CadastrarCampo::class.java)
            startActivity(intent)
        }

        // Botão para Partida Amistosa
        btnPartidaAmistosa.setOnClickListener {
            val intent = Intent(this, PartidaAmistosaActivity::class.java)
            startActivity(intent)
        }

        // Botão para Campeonato
        btnCampeonato.setOnClickListener {
            val intent = Intent(this, CampeonatoActivity::class.java)
            startActivity(intent)
        }

        btnEventos.setOnClickListener {
            val intent = Intent(this, EventosActivity::class.java)
            startActivity(intent)
        }
    }

    // Função para adicionar botões dinamicamente
    private fun adicionarBotoesDinamicamente(
        partidas: List<Partida>,
        campeonatos: List<Campeonato>,
        container: LinearLayout
    ) {
        // Limpa o container antes de adicionar novos botões
        container.removeAllViews()

        // Adiciona botões para partidas
        for (partida in partidas) {
            val botaoPartida = Button(this).apply {
                text = "Partida: ${partida.time1Id} vs ${partida.time2Id} - ${partida.data} ${partida.hora}"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    // Ação ao clicar no botão da partida
                    println("Partida clicada: ${partida.id}")
                }
            }
            container.addView(botaoPartida)
        }

        // Adiciona botões para campeonatos
        for (campeonato in campeonatos) {
            val botaoCampeonato = Button(this).apply {
                text = "Campeonato: ${campeonato.nome}"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    // Ação ao clicar no botão do campeonato
                    println("Campeonato clicado: ${campeonato.id}")
                }
            }
            container.addView(botaoCampeonato)
        }
    }
}
