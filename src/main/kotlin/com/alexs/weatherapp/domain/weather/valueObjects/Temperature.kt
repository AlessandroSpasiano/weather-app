package com.alexs.weatherapp.domain.weather.valueObjects

data class Temperature(
    val min: Double,
    val max: Double,
    val unit: TemperatureUnit = TemperatureUnit.CELSIUS
)