package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName
import com.vladtop.forecast_test_task.data.DTO.HourDTO

data class DayDTO(
    @SerializedName("maxtemp_c") var maxTemp: Double = 0.0,
    @SerializedName("mintemp_c") var minTemp: Double = 0.0,
    @SerializedName("avgtemp_c") var avgTemp: Double = 0.0,
    @SerializedName("condition") var condition: ConditionDTO = ConditionDTO()
)