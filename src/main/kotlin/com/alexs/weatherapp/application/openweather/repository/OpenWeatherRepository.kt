package com.alexs.weatherapp.application.openweather.repository

import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {

    suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): ResultWrapper<OpenWeatherForecast>
}