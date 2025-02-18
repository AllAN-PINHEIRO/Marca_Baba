package com.example.marca_baba.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marca_baba.dao.CampeonatoDAO
import com.example.marca_baba.dao.CampeonatoTimeDAO
import com.example.marca_baba.dao.CampoDAO
import com.example.marca_baba.dao.JogadorDAO
import com.example.marca_baba.dao.PartidaDAO
import com.example.marca_baba.dao.TimeDAO

@Database(
    entities = [
        Time::class,
        Jogador::class,
        Campeonato::class,
        CampeonatoTime::class,
        Campo::class,
        Partida::class
    ],
    version = 4,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun timeDAO(): TimeDAO
    abstract fun jogadorDAO(): JogadorDAO
    abstract fun campoDAO(): CampoDAO
    abstract fun partidaDAO(): PartidaDAO
    abstract fun campeonatoDAO(): CampeonatoDAO
    abstract fun campeonatoTimeDAO(): CampeonatoTimeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marca_baba_db"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}