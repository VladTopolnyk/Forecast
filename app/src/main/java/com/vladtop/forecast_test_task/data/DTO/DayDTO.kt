package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName

data class DayDTO(
    @SerializedName("avgtemp_c" ) var avgTemp  : Double    = 0.0,
    @SerializedName("condition" ) var condition : ConditionDTO = ConditionDTO()
)