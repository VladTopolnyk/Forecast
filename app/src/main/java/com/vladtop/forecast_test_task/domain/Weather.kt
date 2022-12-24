package com.vladtop.forecast_test_task.domain

data class Weather(
    val temperature: Int,
    val weatherState: String,
    val date: String,
    val weatherStateIconUrl: String
)