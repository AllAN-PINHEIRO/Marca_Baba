package com.example.marca_baba

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PartidaAmistosaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_amistosa)

        // Referências para os Spinners e Botão
        val spinnerTimes: Spinner = findViewById(R.id.spinnerTimes)
        val spinnerCampos: Spinner = findViewById(R.id.spinnerCampos)
        val btnIniciarPartida: Button = findViewById(R.id.btnIniciarPartida)

        // Listas de times e campos (reais)
        val listaTimes = DadosPartida.listaTimes.map { it.getNomeTime() }
        val listaCampos = DadosPartida.listaCampos.map { "${it.getRua()}, ${it.getBairro()}, ${it.getCidade()}" }

        // Adapter para os Spinners
        val adapterTimes = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTimes)
        adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTimes.adapter = adapterTimes

        val adapterCampos = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCampos)
        adapterCampos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCampos.adapter = adapterCampos

        // Botão para iniciar a partida
        btnIniciarPartida.setOnClickListener {
            val timeSelecionado = spinnerTimes.selectedItem.toString()
            val campoSelecionado = spinnerCampos.selectedItem.toString()

            // Exibir mensagem com os dados selecionados
            val mensagem = "Partida amistosa iniciada!\nTime: $timeSelecionado\nCampo: $campoSelecionado"
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

            // Aqui você pode adicionar a lógica para iniciar a partida
        }
    }
}