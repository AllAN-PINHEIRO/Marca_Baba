// CadastrarTime.kt
package com.example.marca_baba

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastrarTime : AppCompatActivity() {

    private lateinit var time: Time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrartime)

        val edtNomeTime: EditText = findViewById(R.id.edtNomeTime)
        val edtNomeJogador: EditText = findViewById(R.id.edtNomeJogador)
        val btnAdicionarJogador: Button = findViewById(R.id.btnAdicionarJogador)
        val btnSalvarTime: Button = findViewById(R.id.btnSalvarTime)
        val listaJogadoresLayout: LinearLayout = findViewById(R.id.listaJogadoresLayout)

        // Inicializa o objeto time com o nome do time
        time = Time("")

        // Botão para adicionar jogador ao time
        btnAdicionarJogador.setOnClickListener {
            val nomeJogador = edtNomeJogador.text.toString()

            if (nomeJogador.isNotBlank()) {
                val jogador = Jogador(nomeJogador, "") // Criamos o jogador sem posição por enquanto
                time.adicionaJogador(jogador)

                // Exibe o jogador na lista de jogadores na tela
                val jogadorView = TextView(this)
                jogadorView.text = jogador.nome
                listaJogadoresLayout.addView(jogadorView)

                // Limpar campo de entrada do jogador após adicionar
                edtNomeJogador.text.clear()
            } else {
                Toast.makeText(this, "Digite o nome do jogador", Toast.LENGTH_SHORT).show()
            }
        }

        // Botão para salvar o time completo com os jogadores
        btnSalvarTime.setOnClickListener {
            val nomeTime = edtNomeTime.text.toString()
            if (nomeTime.isNotBlank() && time.getJogadores().isNotEmpty()) {
                time.setNomeTime(nomeTime)
                Toast.makeText(this, "Time ${time.getNomeTime()} salvo com sucesso!", Toast.LENGTH_SHORT).show()

                // Aqui, você pode adicionar lógica adicional para salvar no banco de dados ou finalizar a activity
            } else {
                Toast.makeText(this, "Digite o nome do time e adicione ao menos um jogador", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
