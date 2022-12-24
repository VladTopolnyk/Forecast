package com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vladtop.forecast_test_task.data.mappers.DateConverter
import com.vladtop.forecast_test_task.databinding.HourForecastCardViewBinding
import com.vladtop.forecast_test_task.domain.Hour

class HourForecastRVAdapter(
    private val forecast: List<Hour>
) : RecyclerView.Adapter<HourForecastRVAdapter.HourForecastHolder>() {

    class HourForecastHolder(private val binding: HourForecastCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hour: Hour) {
            setTime(hour.time)
            setTemperature(hour.temperature)
            setIcon(hour.weatherStateIconUrl)
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

        private fun setTime(date: String) {
            val outputDate = DateConverter.toHourDateFormat(date)
            Log.e("formatted", outputDate)
            binding.timeTV.text = outputDate
        }
    }

    override fun getItemCount(): Int = forecast.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourForecastHolder {
        val binding = HourForecastCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HourForecastHolder(binding)
    }

    override fun onBindViewHolder(holder: HourForecastHolder, position: Int) {
        Log.e("day", position.toString())
        holder.bind(forecast[position])
    }

}