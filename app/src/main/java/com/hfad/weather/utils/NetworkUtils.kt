package com.hfad.weather.utils

import android.net.Uri
import java.net.URI
import java.net.URL

class NetworkUtils {

    private val url = "https://api.openweathermap.org/data/2.5/forecast"

    private val PARAM_LAT = "lat"
    private val PARAM_LON = "lon"
    private val PARAM_UNITS = "units"
    private val API_PARAM = "appid"

    private var LAT = ""
    private var LON = ""
    private val API_KEY = "bf5296e547ae9dbee06ec80cda8f6ded"
    private val UNITS = "metric"

    fun builUrl(lat: String, lon: String): URL {

        LAT = lat
        LON = lon

        val uri = Uri.parse(url).buildUpon()
            .appendQueryParameter(PARAM_LAT, LAT)
            .appendQueryParameter(PARAM_LON, LON)
            .appendQueryParameter(PARAM_UNITS, UNITS)
            .appendQueryParameter(API_PARAM, API_KEY)
            .build()

        return URL(uri.toString())
    }
}