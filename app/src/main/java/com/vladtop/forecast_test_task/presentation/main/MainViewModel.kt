package com.vladtop.forecast_test_task.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladtop.forecast_test_task.data.repositories.ForecastRepository
import com.vladtop.forecast_test_task.domain.Forecast
import com.vladtop.forecast_test_task.domain.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {
    private val _forecastLiveData = MutableLiveData<Forecast?>()
    val forecastLiveData: LiveData<Forecast?> = _forecastLiveData

    fun getForecast(city: String) = viewModelScope.launch {
        _forecastLiveData.value = repository.getForecast(city)
    }
}