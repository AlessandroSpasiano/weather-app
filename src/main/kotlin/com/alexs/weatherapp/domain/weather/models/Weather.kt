package com.alexs.weatherapp.domain.weather.models

import com.alexs.weatherapp.domain.weather.valueObjects.City
import com.alexs.weatherapp.domain.weather.valueObjects.Temperature
import com.alexs.weatherapp.domain.weather.valueObjects.Wind
import java.time.Instant

data class Weather(
    val city: City,
    val weatherInfo: List<WeatherInfo>
)