package com.example.marca_baba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marca_baba.data.Jogador
import kotlinx.coroutines.flow.Flow

@Dao
interface JogadorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirJogador(jogador: Jogador): Long

    @Query("SELECT * FROM jogador")
    fun listarTodosJogadores(): Flow<List<Jogador>> // Alterado para Flow

    @Query("SELECT * FROM jogador WHERE nome = :nome")
    suspend fun buscarJogadorPorNome(nome: String): Jogador?

    @Query("SELECT * FROM jogador WHERE timeId = :timeId")
    fun listarJogadorPorTime(timeId: Long): Flow<List<Jogador>> // Alterado para Flow e Long
}