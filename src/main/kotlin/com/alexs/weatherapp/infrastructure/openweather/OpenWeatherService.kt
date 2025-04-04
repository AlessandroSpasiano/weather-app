package com.alexs.weatherapp.infrastructure.openweather

import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("units") units: String = "metric"
    ): OpenWeatherForecast
}