package com.vladtop.forecast_test_task.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vladtop.forecast_test_task.R
import com.vladtop.forecast_test_task.data.mappers.DateConverter.toHourDateFormat
import com.vladtop.forecast_test_task.data.mappers.ForecastConverter
import com.vladtop.forecast_test_task.databinding.FragmentSearchBinding
import com.vladtop.forecast_test_task.domain.Forecast
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.RecyclerViewAdapter.ForecastRVAdapter
import dagger.hilt.android.AndroidEntryPoint

const val POSITION_KEY = "position"
const val FORECAST_KEY = "forecast"

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),
    ForecastRVAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val weatherList = arrayListOf<Weather>()
    private lateinit var forecastRVAdapter: ForecastRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(this)

        searchViewModel.forecastLiveData.observe(viewLifecycleOwner, ::observeForecast)

        provideRecyclerView()

    }

    private fun provideRecyclerView() {
        val recyclerView = binding.forecastRV
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        forecastRVAdapter = ForecastRVAdapter(weatherList, this)
        recyclerView.adapter = forecastRVAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeForecast(forecast: Forecast?) {
        if (forecast == null) {
            noForecastCase()
            return
        }
        updateUI(forecast)
        weatherList.clear()
        weatherList.addAll(forecast.weatherList)
        forecastRVAdapter.notifyDataSetChanged()
        println(weatherList)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(forecast: Forecast) {
        val currentWeather = forecast.weatherList[0]

        binding.cityTV.text =
            "${forecast.city} ${toHourDateFormat(currentWeather.date)}"

        setTemperature(currentWeather.temperature)
        setIcon(currentWeather.weatherStateIconUrl)
    }

    private fun setTemperature(temperature: Int) {
        Log.e("temperature", temperature.toString())
        val temp = if (temperature > 0) "+${temperature}°C" else "${temperature}°C"
        binding.temperatureTV.text = temp
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

    private fun noForecastCase() {
        Toast.makeText(
            requireContext(),
            "We can`t provide forecast for this city!",
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun onSearchClicked(query: String) {
        if (query.length < 2) {
            wrongInputCase()
            return
        }
        searchViewModel.getForecast(query)
    }

    private fun wrongInputCase() {
        Toast.makeText(requireContext(), "Wrong input!", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        navigateToForecastFragment(position)
    }

    private fun navigateToForecastFragment(position: Int) {
        val bundle = bundleOf(
            POSITION_KEY to position,
            FORECAST_KEY to ForecastConverter.toJson(weatherList)
        )
        findNavController().navigate(R.id.action_mainFragment_to_forecastFragment, bundle)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.e("query", query.isNullOrBlank().toString())
        if (query != null) onSearchClicked(query)
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }


}