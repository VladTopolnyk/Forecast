package com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vladtop.forecast_test_task.databinding.ForecastCardViewBinding
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.BaseRVAdapter

class ForecastRVAdapter(
    private val forecast: List<Weather.DayForecast>,
    private val onItemClickListener: OnItemClickListener
) : BaseRVAdapter(forecast){

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(private val binding: ForecastCardViewBinding) : BaseViewHolder(binding) {
        override fun bind(position: Int) {
            val weather = forecast[position]
            binding.run {
                root.setOnClickListener {
                    onItemClickListener.onItemClick(position)
                }
                setIcon(weather.weatherStateIconUrl, weatherStateIV)
                setDate(weather.date, dateTV)
                setTemperature(weather.temperature, temperatureTV)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ForecastCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
}