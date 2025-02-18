package com.example.marca_baba.controller

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.marca_baba.R
import com.example.marca_baba.dao.CampeonatoDAO
import com.example.marca_baba.dao.PartidaDAO
import com.example.marca_baba.data.AppDatabase
import kotlinx.coroutines.launch

class EventosActivity : AppCompatActivity() {

    private lateinit var partidaDAO: PartidaDAO
    private lateinit var campeonatoDAO: CampeonatoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        // Inicializar banco de Dados

        val db = AppDatabase.getDatabase(this)
        partidaDAO = db.partidaDAO()
        campeonatoDAO = db.campeonatoDAO()

        // Referenciar os botões
        val listaPartidas = findViewById<ListView>(R.id.listaPartidas)
        val listaCampeonatos = findViewById<ListView>(R.id.listaCampeonatos)

        // Buscar Partidas
        lifecycleScope.launch {
            val partidas = partidaDAO.listarTodasPartidas()
            val adapterPartidas = ArrayAdapter(
                this@EventosActivity,
                android.R.layout.simple_list_item_1,
                partidas.map { "${it.time1Id} vs ${it.time2Id} - ${it.data} ${it.hora}" }
            )
            listaPartidas.adapter = adapterPartidas
        }

        // Busca e exibe os campeonatos
        lifecycleScope.launch {
            val campeonatos = campeonatoDAO.listarTodosCampeonatos()
            val adapterCampeonatos = ArrayAdapter(
                this@EventosActivity,
                android.R.layout.simple_list_item_1,
                campeonatos.map { it.nome }
            )
            listaCampeonatos.adapter = adapterCampeonatos
        }

//        listaPartidas.setOnItemClickListener { _, _, position, _ ->
//            val partida = partidas[position]
//            val intent = Intent(this, DetalhesPartidaActivity::class.java).apply {
//                putExtra("time1", partida.time1Id.toString())
//                putExtra("time2", partida.time2Id.toString())
//                putExtra("campo", partida.campoId.toString())
//                putExtra("data", partida.data)
//                putExtra("hora", partida.hora)
//            }
//            startActivity(intent)
//        }
    }
}