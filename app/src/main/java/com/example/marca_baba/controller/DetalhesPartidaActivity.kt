package com.example.marca_baba.controller

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.marca_baba.R

class DetalhesPartidaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_partida)

        // Recupera os dados da partida passados pela Intent
        val time1 = intent.getStringExtra("time1")
        val time2 = intent.getStringExtra("time2")
        val campo = intent.getStringExtra("campo")
        val data = intent.getStringExtra("data")
        val hora = intent.getStringExtra("hora")

        // Exibe os dados na tela
        findViewById<TextView>(R.id.txtTime1).text = "Time da Casa: $time1"
        findViewById<TextView>(R.id.txtTime2).text = "Time Visitante: $time2"
        findViewById<TextView>(R.id.txtCampo).text = "Campo: $campo"
        findViewById<TextView>(R.id.txtData).text = "Data: $data"
        findViewById<TextView>(R.id.txtHora).text = "Hora: $hora"
    }
}