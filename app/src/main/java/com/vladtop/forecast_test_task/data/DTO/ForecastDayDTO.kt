package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName

data class ForecastDayDTO (

    @SerializedName("date" ) var date : String = "",
    @SerializedName("day"  ) var day  : DayDTO = DayDTO()
)