package com.hfad.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.hfad.weather.JsonParse.Details
import com.hfad.weather.JsonParse.General
import com.hfad.weather.JsonParse.Weather
import com.hfad.weather.adapter.RecyclerAdapter
import com.hfad.weather.utils.NetworkUtils
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var city: EditText
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: RecyclerAdapter

    lateinit var list: ArrayList<Details>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById(R.id.editTextCity)
        recyclerView = findViewById(R.id.recyclerMain)

        list = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this)



        city.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val flag = imm.hideSoftInputFromWindow(city.windowToken,0)
                if(flag){
                    val name = city.text?.toString()?.trim()
                    if (name != null) {
                        if (!name.isEmpty()) {
                            fetchJson(name)
                        }

                    } else {
                        Toast.makeText(applicationContext, "Enter city", Toast.LENGTH_SHORT)
                    }
                }
                true
            } else {
                false
            }
        }
    }

    fun fetchJson(name: String){

        val url = NetworkUtils().builUrl(name).toString()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fail")
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                val json = GsonBuilder().create().fromJson(body, General::class.java)

                val res = json

                runOnUiThread {
                    list.clear()
                    list = json.list as ArrayList<Details>
                    adapter = RecyclerAdapter(list)
                    recyclerView.adapter = adapter
                }

            }

        })
    }
}