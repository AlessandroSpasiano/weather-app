package com.alexs.weatherapp.application.weather.repository

import com.alexs.weatherapp.domain.weather.models.Weather

interface WeatherForecastRepository {

    suspend fun getWeatherForecastByCityName(
        cityName: String,
        temperatureUnit: String
    ): Weather
}