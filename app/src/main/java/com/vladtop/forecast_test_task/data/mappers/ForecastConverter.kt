package com.vladtop.forecast_test_task.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vladtop.forecast_test_task.domain.Weather

object ForecastConverter {

    fun toJson(list: List<Weather.DayForecast>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Weather.DayForecast>>() {}.type
        return gson.toJson(list, type)
    }

    fun fromJson(json: String): List<Weather.DayForecast> {
        val gson = Gson()
        val type = object : TypeToken<List<Weather.DayForecast>>() {}.type
        return gson.fromJson(json, type)
    }
}