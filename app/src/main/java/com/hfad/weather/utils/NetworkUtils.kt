package com.hfad.weather.utils

import android.net.Uri
import java.net.URI
import java.net.URL

class NetworkUtils {

    private val url = "https://api.openweathermap.org/data/2.5/forecast"

    private val PARAM_CITY = "q"
    private val PARAM_UNITS = "units"
    private val API_PARAM = "appid"

    private var CITY = ""
    private val API_KEY = "bf5296e547ae9dbee06ec80cda8f6ded"
    private val UNITS = "metric"

    fun builUrl(city: String): URL {

        CITY = city

        val uri = Uri.parse(url).buildUpon()
            .appendQueryParameter(PARAM_CITY, CITY)
            .appendQueryParameter(PARAM_UNITS, UNITS)
            .appendQueryParameter(API_PARAM, API_KEY)
            .build()

        return URL(uri.toString())
    }
}