package com.example.marca_baba

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CadastrarJogador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caadastrarjogador)

        // Referências para os campos de entrada
        val edtNomeJogador: EditText = findViewById(R.id.edtNomeJogador)
        val edtPosicaoJogador: EditText = findViewById(R.id.edtPosicaoJogador)

        // Botão para salvar o jogador
        val btnSalvarJogador: Button = findViewById(R.id.btnSalvarJogador)
        btnSalvarJogador.setOnClickListener {
            val nome = edtNomeJogador.text.toString()
            val posicao = edtPosicaoJogador.text.toString()

            // Criar o objeto jogador
            val jogador = Jogador(nome, posicao)
            // Aqui você pode adicionar a lógica para salvar o jogador

            // Limpar os campos após salvar
            edtNomeJogador.text.clear()
            edtPosicaoJogador.text.clear()
        }
    }
}
