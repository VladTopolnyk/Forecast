package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName

data class ConditionDTO(
    @SerializedName("text") var text: String = "",
    @SerializedName("icon") var icon: String = ""
)
