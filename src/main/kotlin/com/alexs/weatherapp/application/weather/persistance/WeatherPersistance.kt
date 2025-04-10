package com.alexs.weatherapp.application.weather.persistance

import com.alexs.weatherapp.domain.weather.models.Weather

interface WeatherPersistance {

    suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather?

    suspend fun saveWeatherForecast(weather: Weather)

}