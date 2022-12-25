package com.vladtop.forecast_test_task.domain

sealed class Weather {

    data class DayForecast(
        val temperature: Int,
        val weatherState: String,
        var date: String,
        val weatherStateIconUrl: String,
        val hoursForecast: List<HourForecast>?
    ) : Weather()

    data class HourForecast(
        val time: String,
        val temperature: Int,
        val weatherStateIconUrl: String
    ) : Weather()
}
