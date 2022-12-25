package com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vladtop.forecast_test_task.databinding.HourForecastCardViewBinding
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.BaseRVAdapter

class HourForecastRVAdapter(
    private val forecast: List<Weather.HourForecast>
) : BaseRVAdapter(forecast) {

    inner class HourForecastHolder(private val binding: HourForecastCardViewBinding) :
        BaseViewHolder(binding) {

        override fun bind(position: Int) {
            val hour = forecast[position]
            binding.run {
                setDate(hour.time, timeTV)
                setTemperature(hour.temperature, temperatureTV)
                setIcon(hour.weatherStateIconUrl, weatherStateIV)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourForecastHolder {
        val binding = HourForecastCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HourForecastHolder(binding)
    }


}