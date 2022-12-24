package com.vladtop.forecast_test_task.data.api

import com.vladtop.pet_project.data.DTO.ForecastDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        private const val API_KEY = "099141aeefb24d4a9c7214818222312"
    }

    @GET("forecast.json?key=$API_KEY")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("days") days: Int = 4,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Response<ForecastDTO>
}