package com.example.marca_baba.controller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.marca_baba.data.DadosPartida
import com.example.marca_baba.R
import com.example.marca_baba.dao.JogadorDAO
import com.example.marca_baba.dao.TimeDAO
import com.example.marca_baba.data.AppDatabase
import kotlinx.coroutines.launch
import com.example.marca_baba.data.Time
import com.example.marca_baba.data.Jogador

class CadastrarTime : AppCompatActivity() {

    private lateinit var spinnerPosicao: Spinner
    private lateinit var edtNomeTime: EditText
    private lateinit var edtNomeJogador: EditText
    private lateinit var btnAdicionarJogador: Button
    private lateinit var btnSalvarTime: Button
    private lateinit var listaJogadoresLayout: LinearLayout
    private lateinit var db: AppDatabase
    private lateinit var timeDAO: TimeDAO
    private lateinit var jogadorDAO: JogadorDAO
    private lateinit var listaJogadores: MutableList<Jogador>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrartime)

        // Inicializa o banco de dados e os Daos
        db = AppDatabase.getDatabase(this)
        timeDAO = db.timeDAO()
        jogadorDAO = db.jogadorDAO()

        // Inicializa as referências dos componentes da tela
        edtNomeTime = findViewById(R.id.edtNomeTime)
        edtNomeJogador = findViewById(R.id.edtNomeJogador)
        spinnerPosicao = findViewById(R.id.spinnerPosicao)
        listaJogadoresLayout = findViewById(R.id.listaJogadoresLayout)
        btnAdicionarJogador = findViewById(R.id.btnAdicionarJogador)
        btnSalvarTime = findViewById(R.id.btnSalvarTime)

        // Inicializa a lista de jogadores
        listaJogadores = mutableListOf()

        // Configura o Spinner de posições
        val posicoesDisponiveis = arrayOf(
            "Goleiro",
            "Zagueiro Esquerdo",
            "Zagueiro Direito",
            "Ala Esquerdo",
            "Ala Direito",
            "Meio-Campo",
            "Atacante"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, posicoesDisponiveis)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPosicao.adapter = adapter

        // Configura o botão de adicionar jogador
        btnAdicionarJogador.setOnClickListener {
            adicionarJogador()
        }

        // Configura o botão de salvar time
        btnSalvarTime.setOnClickListener {
            salvarTime()
        }
    }

    private fun adicionarJogador() {
        val nomeJogador = edtNomeJogador.text.toString().trim()

        // Verifica se o nome do jogador está vazio
        if (nomeJogador.isEmpty()) {
            Toast.makeText(this, "Preencha o nome do jogador!", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se uma posição foi selecionada no Spinner
        if (spinnerPosicao.selectedItem == null) {
            Toast.makeText(this, "Selecione uma posição para o jogador!", Toast.LENGTH_SHORT).show()
            return
        }

        val posicaoJogador = spinnerPosicao.selectedItem.toString()

        // Verifica se a posição já está em uso
        if (listaJogadores.any { it.posicao == posicaoJogador }) {
            Toast.makeText(this, "Já existe um jogador com essa posição!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Verifica se o time já tem 7 jogadores
        if (listaJogadores.size >= 7) {
            Toast.makeText(this, "Máximo de jogadores atingido!", Toast.LENGTH_SHORT).show()
            return
        }

        // Adiciona o jogador à lista
//        val jogador = Jogador(nomeJogador, posicaoJogador)
//        listaJogadores.add(jogador)

        // Adiciona o jogador à lista
        val jogador = com.example.marca_baba.data.Jogador(
            nome = nomeJogador,
            posicao = posicaoJogador,
            timeId = 0
        ) // timeId será definido ao salvar
        listaJogadores.add(jogador)

        // Exibe o jogador na lista de jogadores
        val jogadorView = TextView(this)
        jogadorView.text = "${jogador.nome} - ${jogador.posicao}"
        listaJogadoresLayout.addView(jogadorView)

        // Limpa o campo de nome do jogador
        edtNomeJogador.text.clear()

        // Atualiza o Spinner de posições
        atualizarSpinnerPosicoes()
    }

    private fun atualizarSpinnerPosicoes() {
        // Filtra as posições disponíveis (removendo as já utilizadas)
        val posicoesDisponiveis = arrayOf(
            "Goleiro",
            "Zagueiro Esquerdo",
            "Zagueiro Direito",
            "Ala Esquerdo",
            "Ala Direito",
            "Meio-Campo",
            "Atacante"
        )
            .filter { posicao -> !listaJogadores.any { it.posicao == posicao } }

        // Atualiza o Spinner com as posições disponíveis
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, posicoesDisponiveis)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPosicao.adapter = adapter
    }

    private fun salvarTime() {
        val nomeTime = edtNomeTime.text.toString().trim()

        // Verifica se o nome do time está vazio
        if (nomeTime.isEmpty()) {
            Toast.makeText(this, "Digite o nome do time!", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se o time tem exatamente 7 jogadores
        if (listaJogadores.size != 7) {
            Toast.makeText(this, "O time precisa ter exatamente 7 jogadores!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Verifica se já existe um time com o mesmo nome
        lifecycleScope.launch {
            val timeExistente = timeDAO.listarTimePorNome(nomeTime)
            if (timeExistente != null) {
                Toast.makeText(this@CadastrarTime, "Já existe um time com esse nome!", Toast.LENGTH_SHORT).show()
                return@launch
            }

            // Salva o time no banco de dados (dentro do escopo da corrotina)
            val time = Time(nomeTime = nomeTime)
            val timeId = timeDAO.inserirTime(time)

            // Salva os jogadores no banco de dados
            listaJogadores.forEach { jogador ->
                val jogadorComTimeId = jogador.copy(timeId = timeId) // Associa o jogador ao time
                jogadorDAO.inserirJogador(jogadorComTimeId)
            }

            Toast.makeText(this@CadastrarTime, "Time salvo com sucesso!", Toast.LENGTH_SHORT).show()

            // Limpa os campos após salvar
            edtNomeTime.text.clear()
            edtNomeJogador.text.clear()
            listaJogadores.clear()
            listaJogadoresLayout.removeAllViews()

            // Atualiza o Spinner de posições
            atualizarSpinnerPosicoes()
        }
    }
}