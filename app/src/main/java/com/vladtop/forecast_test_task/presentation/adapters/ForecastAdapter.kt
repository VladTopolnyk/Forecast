package com.vladtop.forecast_test_task.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vladtop.forecast_test_task.databinding.ForecastCardViewBinding
import com.vladtop.forecast_test_task.domain.Weather
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(
    private val forecast: List<Weather>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ForecastViewHolder(private val binding: ForecastCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val view = binding.root

        fun bind(weather: Weather) {
            setDate(weather.date)
            setTemperature(weather.temperature)
            setIcon(weather.weatherStateIconUrl)
        }

        private fun setIcon(url: String) {
            Glide.with(binding.root)
                .load("https:$url")
                .fitCenter()
                .placeholder(android.R.drawable.ic_menu_upload)
                .error(android.R.drawable.stat_notify_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.weatherStateIV)
        }

        private fun setTemperature(temperature: Int) {
            Log.e("temperature", temperature.toString())
            val temp = if (temperature > 0) "+${temperature}°C" else "${temperature}°C"
            binding.temperatureTV.text = temp
        }

        private fun setDate(date: String) {
            val outputDate = changeDateFormat(date)
            Log.e("formatted", outputDate)
            binding.dateTV.text = outputDate
        }

        @SuppressLint("SimpleDateFormat")
        private fun changeDateFormat(date: String): String {
            val inputFormat = SimpleDateFormat("yyyy-mm-dd")
            val outputFormat = SimpleDateFormat("dd MMM")
            val date1 = inputFormat.parse(date)
            return outputFormat.format(date1 as Date)
        }
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        Log.e("day", position.toString())
        holder.bind(forecast[position])
        holder.view.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ForecastViewHolder(binding)
    }
}