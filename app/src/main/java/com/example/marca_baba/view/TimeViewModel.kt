package com.example.marca_baba.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.marca_baba.dao.TimeDAO
import com.example.marca_baba.data.Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeViewModel(private val timeDAO: TimeDAO) : ViewModel() {

    val listaTimes: LiveData<List<Time>> = liveData(Dispatchers.IO) {
        val times = timeDAO.listarTodosTimes()
        emit(times)
    }

    fun inserirTime(time: Time) {
        viewModelScope.launch(Dispatchers.IO) {
            timeDAO.inserirTime(time)
        }
    }
}