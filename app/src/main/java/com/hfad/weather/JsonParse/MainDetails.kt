package com.hfad.weather.JsonParse

import com.google.gson.annotations.SerializedName

data class MainDetails(
    @SerializedName("feels_like")
    val feels_like: String
)