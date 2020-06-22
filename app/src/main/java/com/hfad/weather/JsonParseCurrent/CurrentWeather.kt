package com.hfad.weather.JsonParseCurrent

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("feels_like")
    val feels_like: String,
    @SerializedName("temp_min")
    val temp_min: String,
    @SerializedName("temp_max")
    val temp_max: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("humidity")
    val humidity: String
)