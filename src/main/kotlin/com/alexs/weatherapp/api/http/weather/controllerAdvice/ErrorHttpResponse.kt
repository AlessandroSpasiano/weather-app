package com.alexs.weatherapp.api.http.weather.controllerAdvice

data class ErrorHttpResponse(
    val status: Int,
    val message: String,
    val timestamp: String
)
