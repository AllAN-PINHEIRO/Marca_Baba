package com.example.marca_baba

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastrarCampo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrarcampo)

        // Referências para os campos de entrada
        val edtRua: EditText = findViewById(R.id.edtRua)
        val edtBairro: EditText = findViewById(R.id.edtBairro)
        val edtCidade: EditText = findViewById(R.id.edtCidade)
        val edtCep: EditText = findViewById(R.id.edtCep)

        // Botão para salvar o campo
        val btnSalvarCampo: Button = findViewById(R.id.btnSalvarCampo)
        btnSalvarCampo.setOnClickListener {
            val rua = edtRua.text.toString()
            val bairro = edtBairro.text.toString()
            val cidade = edtCidade.text.toString()
            val cep = edtCep.text.toString()

            // Lógica para validação dos dados do campo
            if (rua.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || cep.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o CEP é válido (8 dígitos)
            if (cep.length != 8 || !cep.all { it.isDigit() }) {
                Toast.makeText(this, "CEP inválido! Deve ter 8 dígitos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Converte o CEP para inteiro
            val cepInt = cep.toIntOrNull() ?: 0

            // Verifica se o campo já existe
            if (DadosPartida.listaCampos.any { it.getRua() == rua && it.getBairro() == bairro && it.getCidade() == cidade }) {
                Toast.makeText(this, "Este campo já está cadastrado!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aqui você pode adicionar a lógica para salvar o campo
            val campo= Campo(rua, bairro, cidade, cep)
            DadosPartida.listaCampos.add(campo) //  Adiciona o campo à lista globalval campo = Campo(rua, bairro, cidade, cep)

            // Limpar os campos após salvar
            edtRua.text.clear()
            edtBairro.text.clear()
            edtCidade.text.clear()
            edtCep.text.clear()
            Toast.makeText(this, "Campo salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}
