package com.example.marca_baba.controller

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.marca_baba.R
import com.example.marca_baba.dao.CampoDAO
import com.example.marca_baba.data.AppDatabase
import kotlinx.coroutines.launch
import com.example.marca_baba.data.Campo

class CadastrarCampo : AppCompatActivity() {

    private lateinit var edtRua: EditText
    private lateinit var edtBairro: EditText
    private lateinit var edtCidade: EditText
    private lateinit var edtCep: EditText
    private lateinit var imgViewCampo: ImageView
    private lateinit var btnSalvarCampo: Button
    private lateinit var btnAdicionarImagem: Button
    private lateinit var db: AppDatabase
    private lateinit var campoDAO: CampoDAO
    private val pickImageRequestCode = 1001
    private lateinit var campoImagemUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrarcampo)

        // Inicializa o banco de dados
        db = AppDatabase.getDatabase(this)
        campoDAO = db.campoDAO()

        // Referências para os campos de entrada
        edtRua = findViewById(R.id.edtRua)
        edtBairro = findViewById(R.id.edtBairro)
        edtCidade = findViewById(R.id.edtCidade)
        edtCep = findViewById(R.id.edtCep)
        imgViewCampo = findViewById(R.id.imgViewCampo)
        btnSalvarCampo = findViewById(R.id.btnSalvarCampo)
        btnAdicionarImagem = findViewById(R.id.btnAdicionarImagem)

        // Configura o botão para adicionar imagem
        btnAdicionarImagem.setOnClickListener {
            abrirGaleria()
        }

        // Configura o botão de salvar campo
        btnSalvarCampo.setOnClickListener {
            salvarCampo()
        }
    }

    // Função para abrir a galeria de imagens
    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, pickImageRequestCode)
    }

    // Recebe a imagem selecionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImageRequestCode) {
            data?.data?.let {
                campoImagemUri = it
                imgViewCampo.setImageURI(campoImagemUri) // Exibe a imagem na UI
            }
        }
    }

    // Função para salvar o campo no banco de dados
    private fun salvarCampo() {
        val rua = edtRua.text.toString().trim()
        val bairro = edtBairro.text.toString().trim()
        val cidade = edtCidade.text.toString().trim()
        val cep = edtCep.text.toString().trim()

        // Validação dos campos
        if (rua.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || cep.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se o CEP contém exatamente 8 dígitos numéricos
        if (!cep.matches(Regex("\\d{8}"))) {
            Toast.makeText(this, "CEP inválido! Deve conter exatamente 8 dígitos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se a imagem foi selecionada
        if (campoImagemUri == null) {
            Toast.makeText(this, "Selecione uma imagem para o campo!", Toast.LENGTH_SHORT).show()
            return
        }

        // Utiliza corrotina para operações no banco de dados
        lifecycleScope.launch {
            val campoExistente = campoDAO.buscarCampo(rua, bairro, cidade)

            if (campoExistente != null) {
                Toast.makeText(
                    this@CadastrarCampo,
                    "Este campo já está cadastrado!",
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            // Salva o campo no banco de dados com a imagem
            val campo = Campo(
                id = 0,
                rua = rua,
                bairro = bairro,
                cidade = cidade,
                cep = cep,
                imagem = campoImagemUri.toString() // Salva a URI da imagem no banco de dados
            )
            campoDAO.inserirCampo(campo)

            Toast.makeText(this@CadastrarCampo, "Campo salvo com sucesso!", Toast.LENGTH_SHORT)
                .show()

            // Limpa os campos após salvar
            limparCampos()
        }
    }

    private fun limparCampos() {
        edtRua.text.clear()
        edtBairro.text.clear()
        edtCidade.text.clear()
        edtCep.text.clear()
        imgViewCampo.setImageURI(null) // Limpa a imagem exibida
    }
}
