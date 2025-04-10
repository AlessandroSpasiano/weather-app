package com.alexs.weatherapp.application.openweather.repository

import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper

interface OpenWeatherRepository {

    suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): ResultWrapper<OpenWeatherForecast>
}