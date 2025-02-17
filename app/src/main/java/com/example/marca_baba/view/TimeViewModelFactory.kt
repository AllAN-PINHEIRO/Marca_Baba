package com.example.marca_baba.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marca_baba.dao.TimeDAO

class TimeViewModelFactory(private val timeDAO: TimeDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeViewModel::class.java)) {
            return TimeViewModel(timeDAO) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}