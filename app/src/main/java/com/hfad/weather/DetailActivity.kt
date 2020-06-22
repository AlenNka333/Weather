package com.hfad.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.hfad.weather.JsonParseCurrent.MainWeather
import com.hfad.weather.JsonParseDetails.Details
import com.hfad.weather.JsonParseDetails.General
import com.hfad.weather.adapter.RecyclerAdapter
import com.hfad.weather.utils.NetworkUtils
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class DetailActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerAdapter

    lateinit var list: ArrayList<Details>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        list = ArrayList()

        recyclerView = findViewById(R.id.recyclerMain)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val lat = intent.getStringExtra("lat")
        val lon = intent.getStringExtra("lon")

        fetchJson(lat, lon)


    }

    fun fetchJson(fir: String, sec: String){

        val url = NetworkUtils().builUrlDetails(fir,sec).toString()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("Fail")
                }

            override fun onResponse(call: okhttp3.Call, response: Response) {

                val body = response.body?.string()
                val json = GsonBuilder().create().fromJson(body, General::class.java)

                runOnUiThread {
                    list.clear()
                    list = json.list as ArrayList<Details>
                    adapter = RecyclerAdapter((list))
                    recyclerView.adapter = adapter
                }

            }

        })
    }

    override fun onBackPressed() {
        finish()
    }
}