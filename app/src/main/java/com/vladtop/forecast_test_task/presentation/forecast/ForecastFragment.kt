package com.vladtop.forecast_test_task.presentation.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vladtop.forecast_test_task.R
import com.vladtop.forecast_test_task.data.mappers.ForecastConverter
import com.vladtop.forecast_test_task.databinding.FragmentForecastBinding
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.forecast_test_task.presentation.adapters.ViewPagerAdapter.ViewPagerAdapter
import com.vladtop.forecast_test_task.presentation.adapters.ViewPagerAdapter.ZoomOutPageTransformer
import com.vladtop.forecast_test_task.presentation.search.FORECAST_KEY
import com.vladtop.forecast_test_task.presentation.search.POSITION_KEY


class ForecastFragment : Fragment(R.layout.fragment_forecast),
    ViewPagerAdapter.OnBackClickListener {

    private var position: Int = 0
    private lateinit var weatherList: ArrayList<Weather>
    private lateinit var binding: FragmentForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION_KEY)
            val json = it.getString(FORECAST_KEY)
            if (json != null) {
                weatherList = ForecastConverter.fromJson(json) as ArrayList<Weather>
                moveToFirst(position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(layoutInflater)
        provideViewPager()
        return binding.root
    }

    private fun provideViewPager() {
        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(weatherList, this, requireContext())
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        provideTabLayout()
    }

    private fun provideTabLayout() {
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, p ->
            tab.text = weatherList[p].date
        }.attach()
    }

    override fun onBackClicked() {
        navigateToSearchFragment()
    }

    private fun navigateToSearchFragment() {
        findNavController().popBackStack()
    }

    private fun moveToFirst(position: Int) {
        val mainGif = weatherList[position]
        weatherList.removeAt(position)
        weatherList.add(0, mainGif)
    }
}