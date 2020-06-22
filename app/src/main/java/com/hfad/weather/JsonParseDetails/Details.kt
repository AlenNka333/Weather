package com.hfad.weather.JsonParseDetails

import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("dt_txt")
    val dt_txt: String,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: MainDetails
)
