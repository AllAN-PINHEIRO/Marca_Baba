package com.example.marca_baba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campeonato")
data class Campeonato(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String
)
