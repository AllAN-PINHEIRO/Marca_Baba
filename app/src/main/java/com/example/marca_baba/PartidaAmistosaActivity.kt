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
        val spinnerTime1: Spinner = findViewById(R.id.spinnerTime1)
        val spinnerTime2: Spinner = findViewById(R.id.spinnerTime2)
        val spinnerCampos: Spinner = findViewById(R.id.spinnerCampos)
        val btnIniciarPartida: Button = findViewById(R.id.btnIniciarPartida)

        // Listas de times e campos (reais)
        val listaTimes = DadosPartida.listaTimes.map { it.getNomeTime() }
        val listaCampos = DadosPartida.listaCampos.map { "${it.getRua()}, ${it.getBairro()}, ${it.getCidade()}" }

        // Adapter para os Spinners de times
        val adapterTimes = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTimes)
        adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTime1.adapter = adapterTimes
        spinnerTime2.adapter = adapterTimes

        // Adapter para o Spinner de campos
        val adapterCampos = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCampos)
        adapterCampos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCampos.adapter = adapterCampos

        // Botão para iniciar a partida
        btnIniciarPartida.setOnClickListener {
            val time1Selecionado = spinnerTime1.selectedItem.toString()
            val time2Selecionado = spinnerTime2.selectedItem.toString()
            val campoSelecionado = spinnerCampos.selectedItem.toString()

            // Verifica se os times são diferentes
            if (time1Selecionado == time2Selecionado) {
                Toast.makeText(this, "Escolha times diferentes!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Exibir mensagem com os dados selecionados
            val mensagem = """
                Partida amistosa iniciada!
                Time 1: $time1Selecionado
                Time 2: $time2Selecionado
                Campo: $campoSelecionado
            """.trimIndent()
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

            // Aqui você pode adicionar a lógica para iniciar a partida
            val partida = Partida(time1Selecionado, time2Selecionado, campoSelecionado)
            DadosPartida.listaPartidas.add(partida)
        }
    }
}