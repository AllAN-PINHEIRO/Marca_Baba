package com.example.marca_baba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botão para cadastrar time
        val btnCadastrarTime: Button = findViewById(R.id.btnCadastrarTime)
        btnCadastrarTime.setOnClickListener {
            val intent = Intent(this, CadastrarTime::class.java)
            startActivity(intent)



        // Botão para cadastrar campo
        val btnCadastrarCampo: Button = findViewById(R.id.btnCadastrarCampo)
        btnCadastrarCampo.setOnClickListener {
            val intent = Intent(this, CadastrarCampo::class.java)
            startActivity(intent)
        }


        }
    }
}
