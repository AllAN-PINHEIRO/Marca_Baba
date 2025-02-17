package com.example.marca_baba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marca_baba.data.Time

@Dao
interface TimeDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserirTime(time: Time) : Long

    @Query("SELECT * FROM time")
    suspend fun listarTodosTimes(): List<Time>

    @Query("SELECT * FROM time WHERE nomeTime = :nome")
    suspend fun listarTimePorNome(nome: String): Time?
}