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

            val campo = Campo(rua, bairro, cidade, cep)
            // Aqui você pode adicionar a lógica para salvar o campo

            // Limpar os campos após salvar
            edtRua.text.clear()
            edtBairro.text.clear()
            edtCidade.text.clear()
            edtCep.text.clear()
            Toast.makeText(this, "Campo salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}
