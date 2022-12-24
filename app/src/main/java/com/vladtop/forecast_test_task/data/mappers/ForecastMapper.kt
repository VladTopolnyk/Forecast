package com.vladtop.pet_project.data.mappers

import com.vladtop.forecast_test_task.data.mappers.DateConverter
import com.vladtop.forecast_test_task.domain.Forecast
import com.vladtop.forecast_test_task.domain.Hour
import com.vladtop.forecast_test_task.domain.Weather
import com.vladtop.pet_project.data.DTO.ForecastDTO
import com.vladtop.pet_project.data.DTO.ForecastDayDTO
import retrofit2.Response
import javax.inject.Inject


class ForecastMapper @Inject constructor() {

    fun transform(response: Response<ForecastDTO>): Forecast? =
        if (response.isSuccessful)
            response.body()?.configureForecast()
        else null

    private fun ForecastDTO.configureForecast(): Forecast? {
        val forecast = arrayListOf<Weather>()
        forecast.addAll(getDayForecast())
        if (forecast.isEmpty()) return null
        return Forecast(location.name, forecast)
    }

    private fun ForecastDTO.getDayForecast(): List<Weather> =
        weekForecast.forecastday.map {
            Weather(
                date = DateConverter.toDayDateFormat(it.date),
                weatherState = it.day.condition.icon,
                temperature = it.day.avgTemp.toInt(),
                weatherStateIconUrl = it.day.condition.icon,
                hoursForecast = it.getHoursForecast()
            )
        }

    private fun ForecastDayDTO.getHoursForecast(): List<Hour> = hoursForecast.map {
        Hour(
            time = it.time,
            temperature = it.temperature.toInt(),
            weatherStateIconUrl = it.conditionDTO.icon
        )
    }
}
