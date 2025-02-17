package com.example.marca_baba.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CampeonatoComTimes(

    @Embedded val campeonato: Campeonato,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CampeonatoTime::class)
    )
    val times: List<Time>

)
