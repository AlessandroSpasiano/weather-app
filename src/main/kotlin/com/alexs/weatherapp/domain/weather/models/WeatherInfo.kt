package com.alexs.weatherapp.domain.weather.models

import com.alexs.weatherapp.domain.weather.valueObjects.Temperature
import com.alexs.weatherapp.domain.weather.valueObjects.Wind
import java.time.Instant

data class WeatherInfo(
    val date: Instant,
    val temperature: Temperature,
    val wind: Wind,
)