package com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vladtop.forecast_test_task.data.mappers.DateConverter
import com.vladtop.forecast_test_task.databinding.ForecastCardViewBinding
import com.vladtop.forecast_test_task.domain.Weather

class ForecastRVAdapter(
    private val forecast: List<Weather>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ForecastRVAdapter.ForecastViewHolder>() {

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
            Log.e("formatted", date)
            binding.dateTV.text = date
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