package com.alexs.weatherapp.api.http.weather.contracts.dto

data class WeatherInfoResponse(
    val minTemperature: String,
    val maxTemperature: String,
    val wind: String,
    val date: String
)
