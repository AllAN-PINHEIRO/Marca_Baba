package com.example.marca_baba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referências para os botões
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
//        btnCampeonato.setOnClickListener {
//            val intent = Intent(this, CampeonatoActivity::class.java) // Substitua pela sua tela de campeonato
//            startActivity(intent)
//        }
    }
}