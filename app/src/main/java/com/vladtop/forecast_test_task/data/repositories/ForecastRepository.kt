package com.vladtop.forecast_test_task.data.repositories

import com.vladtop.forecast_test_task.data.api.ForecastApi
import com.vladtop.forecast_test_task.domain.Forecast
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.pet_project.data.dispatchers.DefaultDispatcherProvider
import com.vladtop.pet_project.data.mappers.ForecastMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastService: ForecastApi,
    private val forecastMapper: ForecastMapper,
    private val dispatchers: DefaultDispatcherProvider
) {
   suspend fun getForecast(city: String): Forecast? = withContext(dispatchers.io) {
        forecastService
            .getForecast(city)
            .let(forecastMapper::transform)
    }
}