package com.hfad.weather.JsonParseDetails

import com.google.gson.annotations.SerializedName

data class MainDetails(
    @SerializedName("feels_like")
    val feels_like: String
)