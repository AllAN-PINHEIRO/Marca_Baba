// CadastrarTime.kt
package com.example.marca_baba

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastrarTime : AppCompatActivity() {

    private lateinit var spinnerPosicao: Spinner
    private lateinit var listaJogadores: MutableList<Jogador>
    private  lateinit var edtNomeTime: EditText
    private lateinit var edtNomeJogador: EditText
    private lateinit var btnAdicionarJogador: Button
    private lateinit var btnSalvarTime: Button
    private lateinit var listaJogadoresLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrartime)

        edtNomeTime = findViewById(R.id.edtNomeTime)
        edtNomeJogador = findViewById(R.id.edtNomeJogador)
        spinnerPosicao = findViewById(R.id.spinnerPosicao)
        listaJogadoresLayout = findViewById(R.id.listaJogadoresLayout)
        btnAdicionarJogador = findViewById(R.id.btnAdicionarJogador)
        btnSalvarTime = findViewById(R.id.btnSalvarTime)
        spinnerPosicao = findViewById(R.id.spinnerPosicao)


        // Inicializa a lista separada de jogadores
        listaJogadores = mutableListOf()


        val posicoes = arrayOf("Goleiro", "Zagueiro Esquerdo", "Zagueiro Direito", "Ala Esquerdo", "Ala Direito", "Meio-Campo", "Atacante")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, posicoes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPosicao.adapter = adapter

        btnAdicionarJogador.setOnClickListener {
            adicionarJogador()
        }

        btnSalvarTime.setOnClickListener {
            salvarTime()
        }

    }

    private fun adicionarJogador() {
        val nomeJogador = edtNomeJogador.text.toString().trim()
        val posicaoJogador = spinnerPosicao.selectedItem.toString()

        if (nomeJogador.isNotEmpty()) {
            if (listaJogadores.size < 7) { // Limite de jogadores
                val jogador = Jogador(nomeJogador, posicaoJogador)
                listaJogadores.add(jogador)

                // Criar um TextView para exibir o jogador na lista
                val jogadorView = TextView(this)
                jogadorView.text = "${jogador.nome} - ${jogador.posicao}"
                listaJogadoresLayout.addView(jogadorView)

                edtNomeJogador.text.clear() // Limpar campo após adicionar
            } else {
                Toast.makeText(this, "Máximo de jogadores atingido!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha o nome do jogador!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun salvarTime() {
        val nomeTime = edtNomeTime.text.toString().trim()

        if (nomeTime.isEmpty()) {
            Toast.makeText(this, "Digite o nome do time!", Toast.LENGTH_SHORT).show()
            return
        }

        if (listaJogadores.size < 7) {
            Toast.makeText(this, "O time precisa ter no mínimo 7 jogadores!", Toast.LENGTH_SHORT).show()
            return
        }

        val time = Time(nomeTime, listaJogadores)

        Toast.makeText(this, "Time salvo com sucesso!", Toast.LENGTH_SHORT).show()

        // Limpar os campos após salvar
        edtNomeTime.text.clear()
        edtNomeJogador.text.clear()
        listaJogadores.clear()
        listaJogadoresLayout.removeAllViews()
    }
}
