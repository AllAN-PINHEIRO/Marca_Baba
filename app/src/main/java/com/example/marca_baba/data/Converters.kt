package com.example.marca_baba.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromList(value: List<Int>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }
}