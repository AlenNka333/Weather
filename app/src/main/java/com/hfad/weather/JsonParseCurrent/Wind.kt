package com.hfad.weather.JsonParseCurrent

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: String
)