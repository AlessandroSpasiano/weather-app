package com.alexs.weatherapp.api.http.weather.contracts.dto

data class WeatherInfoResponse(
    val temperature: String,
    val wind: String,
    val date: String
)
