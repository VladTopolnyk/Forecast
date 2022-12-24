package com.vladtop.forecast_test_task.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vladtop.forecast_test_task.R
import com.vladtop.forecast_test_task.databinding.FragmentMainBinding
import com.vladtop.forecast_test_task.domain.Forecast
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.ForecastAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ForecastAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val forecast = arrayListOf<Weather>()
    private lateinit var forecastAdapter: ForecastAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(this)

        mainViewModel.forecastLiveData.observe(viewLifecycleOwner, ::observeForecast)

        provideRecyclerView()
    }

    private fun provideRecyclerView() {
        val recyclerView = binding.forecastRV
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        forecastAdapter = ForecastAdapter(forecast, this)
        recyclerView.adapter = forecastAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeForecast(response: Forecast?) {
        if (response == null) {
            noForecastCase()
            return
        }
        binding.cityTV.text = response.city
        forecast.clear()
        forecast.addAll(response.forecast)
        forecastAdapter.notifyDataSetChanged()
        println(forecast)
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
        mainViewModel.getForecast(query)
    }

    private fun wrongInputCase() {
        Toast.makeText(requireContext(), "Wrong input!", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        val bundle = bundleOf("someTag" to position)
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