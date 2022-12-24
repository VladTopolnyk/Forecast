package com.vladtop.pet_project.data.mappers

import com.vladtop.forecast_test_task.domain.Forecast
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.pet_project.data.DTO.ForecastDTO
import retrofit2.Response
import javax.inject.Inject


class ForecastMapper @Inject constructor() {

    fun transform(response: Response<ForecastDTO>): Forecast? =
        if (response.isSuccessful)
            response.body()?.configureForecast()
        else null


    private fun ForecastDTO.configureForecast(): Forecast? {
        val forecast = arrayListOf<Weather>()
        forecast.addAll(getForecast())
        if (forecast.isEmpty()) return null
        val currentWeather = getCurrentWeather()
        return Forecast(city = location.name, currentWeather, forecast)
    }


    private fun ForecastDTO.getForecast(): List<Weather> =
        weekForecast.forecastday.map {
            Weather(
                date = it.date,
                weatherState = it.day.condition.icon,
                temperature = it.day.avgTemp.toInt(),
                weatherStateIconUrl = it.day.condition.icon
            )
        }

    private fun ForecastDTO.getCurrentWeather(): Weather = Weather(
        temperature = current.tempC.toInt(),
        date = location.localtime,
        weatherStateIconUrl = current.condition.icon,
        weatherState = current.condition.text
    )
}
