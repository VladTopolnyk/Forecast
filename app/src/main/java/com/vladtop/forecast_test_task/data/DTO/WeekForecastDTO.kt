package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName

data class WeekForecastDTO(
    @SerializedName("forecastday") var forecastday: ArrayList<ForecastDayDTO> = arrayListOf()
)