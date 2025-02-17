package com.example.marca_baba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.marca_baba.data.Campeonato
import com.example.marca_baba.data.CampeonatoComTimes

@Dao
interface CampeonatoDAO {

    // Inserir um novo campeonato
    @Insert
    suspend fun inserirCampeonato(campeonato: Campeonato)

    // Listar todos os campeonatos
    @Query("SELECT * FROM campeonato")
    suspend fun listarTodosCampeonatos(): List<Campeonato>

    // Buscar campeonato pelo nome
    @Query("SELECT * FROM campeonato WHERE nome = :nome")
    suspend fun buscarCampeonatoPorNome(nome: String): Campeonato?

    // Buscar campeonato pelo id
    @Query("SELECT * FROM campeonato WHERE id = :id")
    suspend fun buscarCampeonatoPorId(id: Int): Campeonato?

    // Buscar campeonato com times
    @Transaction
    @Query("SELECT * FROM campeonato WHERE id = :campeonatoId")
    suspend fun buscarCampeonatoComTimes(campeonatoId: Int): CampeonatoComTimes
}