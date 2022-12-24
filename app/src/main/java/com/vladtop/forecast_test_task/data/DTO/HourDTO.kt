package com.vladtop.forecast_test_task.data.DTO

import com.google.gson.annotations.SerializedName
import com.vladtop.pet_project.data.DTO.ConditionDTO

data class HourDTO(
    @SerializedName("time") var time: String = "",
    @SerializedName("temp_c") var temperature: Double = 0.0,
    @SerializedName("condition") var conditionDTO: ConditionDTO = ConditionDTO()
)
