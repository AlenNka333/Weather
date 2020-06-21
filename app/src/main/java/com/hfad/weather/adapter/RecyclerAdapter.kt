package com.hfad.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.weather.JsonParse.Details
import com.hfad.weather.JsonParse.General
import com.hfad.weather.R
import com.hfad.weather.JsonParse.Weather
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val list: ArrayList<Details>): RecyclerView.Adapter<RecyclerAdapter.WeatherHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.WeatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item_detail, parent, false)
        return WeatherHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.WeatherHolder, position: Int) {

        val details = list[position]
        holder.title.text = details.dt_txt
        holder.subtitle.text = details.weather[0].description
        holder.temperature.text = String.format("%s°C",details.main.feels_like)

        val iconUrl = "https://openweathermap.org/img/w/${details.weather[0].icon}.png"
        Picasso.get()
            .load(iconUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {

        }

    }

    inner class WeatherHolder(v: View): RecyclerView.ViewHolder(v){
        val imageView: ImageView = v.findViewById(R.id.weatherIcon)
        var title: TextView = v.findViewById(R.id.weather_list_title)
        val subtitle: TextView = v.findViewById(R.id.weather_list_subtitle)
        val temperature: TextView = v.findViewById(R.id.temperature)
    }
}