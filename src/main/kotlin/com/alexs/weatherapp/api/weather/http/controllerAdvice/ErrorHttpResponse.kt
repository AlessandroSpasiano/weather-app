package com.alexs.weatherapp.api.weather.http.controllerAdvice

data class ErrorHttpResponse(
    val status: Int,
    val message: String,
    val timestamp: String
)
