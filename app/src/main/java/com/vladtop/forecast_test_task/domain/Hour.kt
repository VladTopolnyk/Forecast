package com.vladtop.forecast_test_task.domain

data class Hour(
    val time: String,
    val temperature: Int,
    val weatherStateIconUrl: String
)
