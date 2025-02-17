package com.example.marca_baba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time")
data class Time(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nomeTime: String
)
