package com.hfad.weather.JsonParseCurrent

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String
)