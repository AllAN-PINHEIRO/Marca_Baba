package com.example.marca_baba

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class PartidaAmistosaActivity : AppCompatActivity() {
    private lateinit var spinnerTime1: Spinner
    private lateinit var spinnerTime2: Spinner
    private lateinit var spinnerCampos: Spinner
    private lateinit var btnSelecionarData: Button
    private lateinit var btnSelecionarHora: Button
    private lateinit var btnIniciarPartida: Button
    private var txtDataSelecionada: TextView? = null
    private var txtHoraSelecionada: TextView? = null
    private var dataSelecionada = ""
    private var horaSelecionada = ""

    private var listaTimes: List<String>? = null // Lista de times disponíveis
    private var listaCampos: List<String>? = null // Lista de campos disponíveis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_amistosa)

        // Inicializando componentes
        spinnerTime1 = findViewById(R.id.spinnerTime1)
        spinnerTime2 = findViewById(R.id.spinnerTime2)
        spinnerCampos = findViewById(R.id.spinnerCampos)
        btnSelecionarData = findViewById(R.id.btnSelecionarData)
        btnSelecionarHora = findViewById(R.id.btnSelecionarHora)
        btnIniciarPartida = findViewById(R.id.btnIniciarPartida)
        txtDataSelecionada = findViewById(R.id.txtDataSelecionada)
        txtHoraSelecionada = findViewById(R.id.txtHoraSelecionada)

        // Simulando dados de times e campos (aqui podia entrar um banco de dados)
        listaTimes = listOf("Time A", "Time B", "Time C")
        listaCampos = listOf("Campo 1", "Campo 2", "Campo 3")

        // Configurando os Spinners
        configurarSpinner(spinnerTime1, listaTimes!!)
        configurarSpinner(spinnerTime2, listaTimes!!)
        configurarSpinner(spinnerCampos, listaCampos!!)

        // botões de data e hora
        btnSelecionarData.setOnClickListener(View.OnClickListener { v: View? -> abrirDatePicker() })
        btnSelecionarHora.setOnClickListener(View.OnClickListener { v: View? -> abrirTimePicker() })

        // Botão para iniciar a partida
        btnIniciarPartida.setOnClickListener(View.OnClickListener { v: View? -> iniciarPartida() })
    }

    private fun configurarSpinner(spinner: Spinner, dados: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun abrirDatePicker() {
        val calendario = Calendar.getInstance()
        val ano = calendario[Calendar.YEAR]
        val mes = calendario[Calendar.MONTH]
        val dia = calendario[Calendar.DAY_OF_MONTH]

        val datePicker =
            DatePickerDialog(this, { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                dataSelecionada = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                txtDataSelecionada!!.text = dataSelecionada
            }, ano, mes, dia)
        datePicker.show()
    }

    private fun abrirTimePicker() {
        val calendario = Calendar.getInstance()
        val hora = calendario[Calendar.HOUR_OF_DAY]
        val minuto = calendario[Calendar.MINUTE]

        val timePicker = TimePickerDialog(this, { view: TimePicker?, hourOfDay: Int, minute: Int ->
            horaSelecionada = String.format("%02d:%02d", hourOfDay, minute)
            txtHoraSelecionada!!.text = horaSelecionada
        }, hora, minuto, true)
        timePicker.show()
    }

    private fun iniciarPartida() {
        val time1 = spinnerTime1!!.selectedItem.toString()
        val time2 = spinnerTime2!!.selectedItem.toString()
        val campo = spinnerCampos!!.selectedItem.toString()

        if (time1 == time2) {
            Toast.makeText(this, "Os times devem ser diferentes!", Toast.LENGTH_SHORT).show()
            return
        }

        if (dataSelecionada.isEmpty() || horaSelecionada.isEmpty()) {
            Toast.makeText(this, "Selecione data e hora!", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, DadosPartidaActivity::class.java)
        Log.d("PartidaAmistosaActivity", "Iniciando partida com: $time1, $time2, $campo, $dataSelecionada, $horaSelecionada")
        intent.putExtra("time1", time1)
        intent.putExtra("time2", time2)
        intent.putExtra("campo", campo)
        intent.putExtra("data", dataSelecionada)
        intent.putExtra("hora", horaSelecionada)
        startActivity(intent)

        intent.putExtra("time1", time1)
        intent.putExtra("time2", time2)
        intent.putExtra("campo", campo)
        intent.putExtra("data", dataSelecionada)
        intent.putExtra("hora", horaSelecionada)
        startActivity(intent)
    }
}