package com.example.marca_baba.data

import com.example.marca_baba.model.Campeonato
import com.example.marca_baba.model.Campo
import com.example.marca_baba.model.Partida
import com.example.marca_baba.model.Time

object DadosPartida {
    // Listar os times que estão cadastrados
    val listaTimes = mutableListOf<Time>()

    // Listar os campos que estão cadastrados
    val listaCampos = mutableListOf<Campo>()

    // Listar as partidas que estão cadastradas
    val listaPartidas = mutableListOf<Partida>()

    // Listar os campeonatos que estão cadastrados
    val listaCampeonatos = mutableListOf<Campeonato>()

}