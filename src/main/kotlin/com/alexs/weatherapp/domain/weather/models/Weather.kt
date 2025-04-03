package com.alexs.weatherapp.domain.weather.models

import com.alexs.weatherapp.domain.weather.valueObjects.City
import com.alexs.weatherapp.domain.weather.valueObjects.Temperature
import com.alexs.weatherapp.domain.weather.valueObjects.Wind
import java.time.Instant

data class Weather(
    val date: Instant,
    val temperature: Temperature,
    val wind: Wind,
    val city: City
)