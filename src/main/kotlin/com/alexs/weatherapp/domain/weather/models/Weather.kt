package com.alexs.weatherapp.domain.weather.models

import com.alexs.weatherapp.domain.weather.valueObjects.City
import java.io.Serializable


data class Weather(
    val city: City,
    val weatherInfo: List<WeatherInfo>
): Serializable