package com.vladtop.forecast_test_task.presentation.adapters.ViewPagerAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vladtop.forecast_test_task.databinding.ViewPagerItemBinding
import com.vladtop.forecast_test_task.domain.Hour
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter.HourForecastRVAdapter
import java.util.*

class ViewPagerAdapter(
    private val weatherList: List<Weather>,
    private val onBackClickListener: OnBackClickListener,
    private val context: Context
) : RecyclerView.Adapter<ViewPagerAdapter.VPViewHolder>(

) {
    interface OnBackClickListener {
        fun onBackClicked()
    }

    inner class VPViewHolder(private val binding: ViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val hourForecast = arrayListOf<Hour>()

        fun bind(weather: Weather) {
            binding.backBtn.setOnClickListener {
                onBackClickListener.onBackClicked()
            }
            setIcon(weather.weatherStateIconUrl)
            setTemperature(weather.temperature)
            setDate(weather.date)

            if (weather.hoursForecast != null) hourForecast.addAll(weather.hoursForecast)
            provideRecyclerView()
        }

        private fun setTemperature(temperature: Int) {
            Log.e("temperature", temperature.toString())
            val temp = if (temperature > 0) "+${temperature}°C" else "${temperature}°C"
            binding.temperatureTV.text = temp
        }


        @SuppressLint("SetTextI18n")
        private fun setDate(date: String) {
            Log.e("formatted", date)
            binding.cityTV.text = date
        }

        private fun provideRecyclerView() {
            binding.hourForecastRV.layoutManager =
                GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
            binding.hourForecastRV.adapter = HourForecastRVAdapter(hourForecast)
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPViewHolder {
        val binding = ViewPagerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VPViewHolder(binding)
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: VPViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }
}