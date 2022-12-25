package com.vladtop.forecast_test_task.presentation.adapters.ViewPagerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vladtop.forecast_test_task.databinding.ViewPagerItemBinding
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.BaseRVAdapter
import com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter.HourForecastRVAdapter

class ViewPagerAdapter(
    private val forecast: List<Weather.DayForecast>,
    private val onBackClickListener: OnBackClickListener,
    private val context: Context
) : BaseRVAdapter(forecast) {
    interface OnBackClickListener {
        fun onBackClicked()
    }

    inner class ViewHolder(private val binding: ViewPagerItemBinding) :
        BaseRVAdapter.BaseViewHolder(binding) {

        private val hourForecast = arrayListOf<Weather.HourForecast>()
        override fun bind(position: Int) {
            val weather = forecast[position]

            binding.run {
                backBtn.setOnClickListener {
                    onBackClickListener.onBackClicked()
                }
                setIcon(weather.weatherStateIconUrl, weatherStateIV)
                setTemperature(weather.temperature, temperatureTV)
                setDate(weather.date, cityTV)
            }
            if (weather.hoursForecast != null) hourForecast.addAll(weather.hoursForecast)
            provideRecyclerView()
        }

        private fun provideRecyclerView() {
            binding.hourForecastRV.layoutManager =
                GridLayoutManager(
                    context,
                    binding.hourForecastRV.autoFitColumns(130),
                    GridLayoutManager.VERTICAL,
                    false
                )
            binding.hourForecastRV.adapter = HourForecastRVAdapter(hourForecast)
        }

        private fun RecyclerView.autoFitColumns(columnWidth: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return ((displayMetrics.widthPixels / displayMetrics.density) / columnWidth).toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewPagerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
}