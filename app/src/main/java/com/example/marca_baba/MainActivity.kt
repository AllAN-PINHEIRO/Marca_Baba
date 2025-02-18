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
import com.example.marca_baba.data.Eventos

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // botão eventos addicionado aqui
        val btnEventos: Button = findViewById(R.id.btnEventos)

        btnEventos.setOnClickListener {
            val intent = Intent(this, EventosActivity::class.java)
            startActivity(intent)
        }


        // Referências para os botões fixos
        val btnCadastrarTime: Button = findViewById(R.id.btnCadastrarTime)
        val btnCadastrarCampo: Button = findViewById(R.id.btnCadastrarCampo)
        val btnPartidaAmistosa: Button = findViewById(R.id.btnPartidaAmistosa)
        val btnCampeonato: Button = findViewById(R.id.btnCampeonato)

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
    }

    // Função para adicionar botões dinamicamente
    private fun adicionarBotoesDinamicamente(eventos: Eventos, container: LinearLayout) {
        for (evento in eventos.getEventos()) {
            val botao = Button(this)
            botao.text = evento.toString() // Nome do evento como texto do botão
            botao.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // Adiciona ação ao botão
            botao.setOnClickListener {
                // Aqui você pode definir o que acontece ao clicar no botão do evento
                println("Botão clicado: ${evento.toString()}")
            }

            // Adiciona o botão ao layout
            container.addView(botao)
        }
    }
}
