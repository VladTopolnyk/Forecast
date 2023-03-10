package com.vladtop.forecast_test_task.presentation.search

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private val weatherList = arrayListOf<Weather.DayForecast>()
    private lateinit var forecastRVAdapter: ForecastRVAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    private val locationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    //granted
                    if (isInternetAvailable()) {
                        //get Coordinates
                        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                            launchProgressBar(false, ProgressBar.VISIBLE)
                            searchViewModel.getForecast("${location?.latitude},${location?.longitude}")
                        }
                    } else onInternetDisconnected()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    //denied for the first time
                }
                else -> {
                    //deniedForever
                    showExplanationDialog()
                }
            }
        }

    private fun onInternetDisconnected() {
        Toast.makeText(requireContext(), "Internet Disconnected!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle
        ?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(this)

        searchViewModel.forecastLiveData.observe(viewLifecycleOwner, ::observeForecast)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding.locationTV.setOnClickListener {
            onLocationClicked()
        }
        provideRecyclerView()
    }

    private fun onLocationClicked() {
        onLocationPermission()
    }

    private fun onLocationPermission() {
        locationPermission.launch(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
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
        launchProgressBar(true, ProgressBar.INVISIBLE)
        if (forecast == null) {
            noForecastCase()
            return
        }
        updateUI(forecast)
        weatherList.clear()
        weatherList.addAll(forecast.dayList)
        forecastRVAdapter.notifyDataSetChanged()
        println(weatherList)
    }

    private fun launchProgressBar(isEnabled: Boolean, visible: Int) {
        binding.root.isEnabled = isEnabled
        binding.progressBar.visibility = visible
    }


    @SuppressLint("SetTextI18n")
    private fun updateUI(forecast: Forecast) {
        val currentWeather = forecast.dayList[0]

        binding.cityTV.text =
            "${forecast.city} ${toHourDateFormat(currentWeather.date)}"

        setTemperature(currentWeather.temperature)
        setIcon(currentWeather.weatherStateIconUrl)
    }

    private fun setTemperature(temperature: Int) {
        Log.e("temperature", temperature.toString())
        val temp = if (temperature > 0) "+${temperature}??C" else "${temperature}??C"
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

    private fun isInternetAvailable()
            : Boolean {
        val result: Boolean
        val connectivityManager =
            requireActivity().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities)
                ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }

    private fun onSearchClicked(query: String) {
        if (query.trim().length < 2) {
            wrongInputCase()
            return
        }
        if (isInternetAvailable()) {
            launchProgressBar(false, ProgressBar.VISIBLE)

            searchViewModel.getForecast(query)

        } else onInternetDisconnected()
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
        findNavController().navigate(
            R.id.action_mainFragment_to_forecastFragment,
            bundle
        )
    }

    override fun onQueryTextSubmit(
        query: String?
    )
            : Boolean {
        Log.e("query", query.isNullOrBlank().toString())
        if (query != null) onSearchClicked(query)
        return false
    }

    override fun onQueryTextChange(
        p0: String?
    )
            : Boolean {
        return false
    }


    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun showExplanationDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("This provides weather to your location!")
            .setTitle("Allow Location Permission!")
            .setPositiveButton(
                "Open Settings"
            ) { _, _ ->
                openSettings()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()
        dialog.show()
    }
}
