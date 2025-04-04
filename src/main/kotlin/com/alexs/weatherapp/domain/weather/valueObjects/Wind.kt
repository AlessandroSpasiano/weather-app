package com.alexs.weatherapp.domain.weather.valueObjects

data class Wind(
    val value: Double,
    val unit: WindUnit = WindUnit.KILOMETERS_PER_HOUR
)