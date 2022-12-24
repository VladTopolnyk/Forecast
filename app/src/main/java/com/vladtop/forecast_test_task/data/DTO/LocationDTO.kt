package com.vladtop.pet_project.data.DTO

import com.google.gson.annotations.SerializedName


data class LocationDTO(
    @SerializedName("name") var name: String = "",
    @SerializedName("country") var country: String = "",
    @SerializedName("localtime") var localtime: String = ""
)
