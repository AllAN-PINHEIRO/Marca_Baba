package com.example.marca_baba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marca_baba.data.CampeonatoTime

@Dao
interface CampeonatoTimeDAO {

    // Inserir um novo relacionamento entre campeonato e time
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserirCampeonatoTime(campeonatoTime: CampeonatoTime)

    // Listar todos os relacionamentos entre campeonatos e times
    @Query("SELECT * FROM campeonato_time")
    suspend fun listarTodosCampeonatoTimes(): List<CampeonatoTime>

    // Buscar todos os times de um campeonato específico
    @Query("SELECT * FROM campeonato_time WHERE campeonatoId = :campeonatoId")
    suspend fun listarTimesPorCampeonato(campeonatoId: Int): List<CampeonatoTime>

    // Buscar todos os campeonatos de um time específico
    @Query("SELECT * FROM campeonato_time WHERE idTime = :timeId")
    suspend fun listarCampeonatosPorTime(timeId: Int): List<CampeonatoTime>
}