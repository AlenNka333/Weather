package com.hfad.weather

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import android.location.Address
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.hfad.weather.JsonParseCurrent.MainWeather
import com.hfad.weather.utils.NetworkUtils
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var city: EditText
    lateinit var update: TextView

    lateinit var status: TextView
    lateinit var temperature: TextView
    lateinit var min_temp: TextView
    lateinit var max_temp: TextView

    lateinit var sunrise: TextView
    lateinit var sunset: TextView
    lateinit var humidity: TextView
    lateinit var pressure: TextView
    lateinit var wind: TextView

    lateinit var button: Button

    var lat = ""
    var lon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById(R.id.editTextCity)
        update = findViewById(R.id.textViewUpdate)
        status = findViewById(R.id.status)
        temperature = findViewById(R.id.temperature)
        min_temp = findViewById(R.id.min_temp)
        max_temp = findViewById(R.id.max_temp)
        sunrise = findViewById(R.id.sunrise)
        sunset = findViewById(R.id.sunset)
        humidity = findViewById(R.id.humidity)
        pressure = findViewById(R.id.pressure)
        wind = findViewById(R.id.wind)

        button = findViewById(R.id.button)

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

        try {
            val geocoder = Geocoder(this)
            val address: List<Address> = geocoder.getFromLocationName(name, 1)
            if (address.isEmpty()) {
                Toast.makeText(this, "Check city name", Toast.LENGTH_SHORT).show()
            }
            val location = address[0]
            lat = location.getLatitude().toString()
            lon = location.getLongitude().toString()
        }catch (e: IOException){
            when{
                e.message == "grpc failed" -> {
                    Toast.makeText(applicationContext, "check internet connection or city name", Toast.LENGTH_SHORT)
                    .show()}
                else -> throw e
            }
        }


        val url = NetworkUtils().builUrlCurrent(lat, lon).toString()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fail")
                Toast.makeText(applicationContext, "Check city name", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                val json = GsonBuilder().create().fromJson(body, MainWeather::class.java)

                runOnUiThread {
                    setData(json)
                }

            }

        })
    }

    fun setData( data: MainWeather){
        val time = data.updateTime.toLong()
        update.text = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(time*1000))

        status.text = data.weather[0].status

        temperature.text = "${data.main.feels_like}°C"

        min_temp.text = "Min temp ${data.main.temp_min}°C"
        max_temp.text = "Max temp ${data.main.temp_max}°C"

        pressure.text = data.main.pressure
        humidity.text = data.main.humidity
        wind.text = data.wind.speed

        sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(data.sys.sunrise.toLong()*1000))
        sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(data.sys.sunset.toLong()*1000))

    }

    fun onClickMoreDetails(view: View) {
        val intent = Intent(this, DetailActivity::class.java)
        if(!lat.isEmpty() && !lon.isEmpty()) {
            intent.putExtra("lat", lat)
            intent.putExtra("lon", lon)
            startActivity(intent)
        }
        else Toast.makeText(applicationContext, "check city name", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}