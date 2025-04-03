package com.alexs.weatherapp.domain.weather.valueObjects

data class Temperature(
    val value: Double,
    val unit: TemperatureUnit = TemperatureUnit.CELSIUS
)