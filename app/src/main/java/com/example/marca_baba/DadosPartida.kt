package com.example.marca_baba

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DadosPartidaActivity : AppCompatActivity() {
    // vou criar assim mesmo
    private lateinit var etAssistencia1: EditText
    private lateinit var etGol1: EditText
    private lateinit var etAmarelo1: EditText
    private lateinit var etVermelho1: EditText
    private lateinit var etAssistencia2: EditText
    private lateinit var etGol2: EditText
    private lateinit var etAmarelo2: EditText
    private lateinit var etVermelho2: EditText
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_dados_partida)

        // começa aqui
        etAssistencia1 = findViewById(R.id.etAssistencia1)
        etGol1 = findViewById(R.id.etGol1)
        etAmarelo1 = findViewById(R.id.etAmarelo1)
        etVermelho1 = findViewById(R.id.etVermelho1)
        etAssistencia2 = findViewById(R.id.etAssistencia2)
        etGol2 = findViewById(R.id.etGol2)
        etAmarelo2 = findViewById(R.id.etAmarelo2)
        etVermelho2 = findViewById(R.id.etVermelho2)
        btnSalvar = findViewById(R.id.btnSalvar)

        // acho que deve ter um jeito melhor que criar duas variveis de cada ? possivelmente mas tenho que mexer ainda
        btnSalvar.setOnClickListener {
            val assistencia1 = etAssistencia1.text.toString().toInt()
            val gol1 = etGol1.text.toString().toInt()
            val amarelo1 = etAmarelo1.text.toString().toInt()
            val vermelho1 = etVermelho1.text.toString().toInt()

            val assistencia2 = etAssistencia2.text.toString().toInt()
            val gol2 = etGol2.text.toString().toInt()
            val amarelo2 = etAmarelo2.text.toString().toInt()
            val vermelho2 = etVermelho2.text.toString().toInt()

            // Salvar dados e exibe uma mensangem fuleira de salvamento
            Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()


            }

        }
}
