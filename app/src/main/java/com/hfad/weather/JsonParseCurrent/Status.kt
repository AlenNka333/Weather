package com.hfad.weather.JsonParseCurrent

import com.google.gson.annotations.SerializedName

data class Status (
    @SerializedName("main")
    val status: String
)