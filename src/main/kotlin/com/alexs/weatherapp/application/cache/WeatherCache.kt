package com.alexs.weatherapp.application.cache

import com.alexs.weatherapp.domain.weather.models.Weather

interface WeatherCache {

    suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather?

    suspend fun putWeatherForecastByCityName(cityName: String, weather: Weather)

}