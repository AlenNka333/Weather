package com.hfad.weather.JsonParseDetails

import com.google.gson.annotations.SerializedName

data class General(
    @SerializedName("list")
    val list: List<Details>
)
