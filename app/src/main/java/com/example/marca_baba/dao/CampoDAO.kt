package com.example.marca_baba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marca_baba.data.Campo
import kotlinx.coroutines.flow.Flow

@Dao
interface CampoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirCampo(campo: Campo): Long

    @Query("SELECT * FROM campo")
    fun listarTodosCampos(): Flow<List<Campo>> // Alterado para Flow

    @Query("SELECT * FROM campo WHERE id = :id")
    suspend fun buscarCampoPorId(id: Long): Campo?

    @Query("SELECT * FROM campo WHERE cidade LIKE '%' || :cidade || '%'")
    fun listarCamposPorCidade(cidade: String): Flow<List<Campo>> // Alterado para Flow e LIKE

    @Query("SELECT * FROM campo WHERE rua = :rua AND bairro = :bairro AND cidade = :cidade LIMIT 1")
    suspend fun buscarCampo(rua: String, bairro: String, cidade: String): Campo?
}