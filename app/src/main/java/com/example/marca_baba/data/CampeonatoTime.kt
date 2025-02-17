package com.example.marca_baba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campeonato_time")
data class CampeonatoTime(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val campeonatoId: Int, //FK
    val idTime: Int //FK
)
