package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName

data class CurrentDTO(
    @SerializedName("temp_c") var tempC: Double = 0.0,
    @SerializedName("condition") var condition: ConditionDTO = ConditionDTO()
)
