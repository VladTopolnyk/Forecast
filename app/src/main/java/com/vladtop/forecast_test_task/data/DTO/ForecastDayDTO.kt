package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName
import com.vladtop.forecast_test_task.data.DTO.HourDTO

data class ForecastDayDTO(

    @SerializedName("date") var date: String = "",
    @SerializedName("day") var day: DayDTO = DayDTO(),
    @SerializedName("hour") var hoursForecast: ArrayList<HourDTO> = arrayListOf()
)