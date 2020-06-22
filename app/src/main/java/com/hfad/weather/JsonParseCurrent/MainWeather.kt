package com.hfad.weather.JsonParseCurrent

import com.google.gson.annotations.SerializedName

data class MainWeather(
    @SerializedName("main")
    val main: CurrentWeather,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt")
    val updateTime: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("weather")
    val weather: List<Status>
)