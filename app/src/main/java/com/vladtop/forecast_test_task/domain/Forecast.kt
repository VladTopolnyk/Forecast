package com.vladtop.forecast_test_task.domain

data class Forecast(
    val city: String,
    val weatherList: List<Weather>
)