package com.example.marca_baba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marca_baba.data.Partida

@Dao
interface PartidaDAO {

    // Inserir uma partida
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirPartida(partida: Partida)

    // Listar todas as partidas
    @Query("SELECT * FROM partida")
    suspend fun listarTodasPartidas(): List<Partida>

    // Buscar partida pelo ID
    @Query("SELECT * FROM partida WHERE id = :id")
    suspend fun buscarPartidaPorId(id: Int): Partida?

    // Listar partidas de um time específico
    @Query("SELECT * FROM partida WHERE time1Id = :timeId OR time2Id = :timeId")
    suspend fun listarPartidasPorTime(timeId: Int): List<Partida>

    // Listar partidas de um campo específico
    @Query("SELECT * FROM partida WHERE campoId = :campoId")
    suspend fun listarPartidasPorCampo(campoId: Int): List<Partida>
}