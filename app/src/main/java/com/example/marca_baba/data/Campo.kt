package com.example.marca_baba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campo")
data class Campo(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val rua: String,
    val bairro: String,
    val cidade: String,
    val cep: String,
    val imagem: String
)
