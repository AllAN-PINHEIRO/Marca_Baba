package com.example.marca_baba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jogador")
data class Jogador(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val posicao: String,
    val timeId: Long //FK
)
