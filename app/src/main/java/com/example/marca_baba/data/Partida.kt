package com.example.marca_baba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partida")
data class Partida(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time1Id: Int, //FK
    val time2Id: Int, //FK
    val campoId: Int, //FK
    val data: String,
    val hora: String
)
