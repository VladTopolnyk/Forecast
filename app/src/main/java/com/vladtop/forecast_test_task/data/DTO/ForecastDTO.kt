package com.vladtop.pet_project.data.DTO



import com.google.gson.annotations.SerializedName

data class ForecastDTO(
    @SerializedName("location" ) var location : LocationDTO = LocationDTO(),
    @SerializedName("current"  ) var current  : CurrentDTO  = CurrentDTO(),
    @SerializedName("forecast" ) var weekForecast : WeekForecastDTO = WeekForecastDTO()
)