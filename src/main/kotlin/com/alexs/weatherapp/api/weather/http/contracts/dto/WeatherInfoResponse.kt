package com.alexs.weatherapp.api.weather.http.contracts.dto

data class WeatherInfoResponse(
    val minTemperature: String,
    val maxTemperature: String,
    val wind: String,
    val date: String,
    val description: String
)
