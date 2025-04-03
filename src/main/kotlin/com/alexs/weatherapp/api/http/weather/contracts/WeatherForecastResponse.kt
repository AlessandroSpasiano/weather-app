package com.alexs.weatherapp.api.http.weather.contracts

import com.alexs.weatherapp.domain.weather.models.Weather

data class WeatherForecastResponse(
    val city: String,
    val temperature: String,
    val wind: String,
    val date: String
)

fun Weather.toResponse(): WeatherForecastResponse {
    return WeatherForecastResponse(
        city = this.city.name,
        temperature = "${this.temperature.value.toTemperatureString()} ${this.temperature.unit}",
        wind = "${this.wind.value.toWindString()} ${this.wind.unit}",
        date = this.date.toString()
    )
}

fun Double.toTemperatureString(): String {
    return String.format("%.1f", this)
}

fun Double.toWindString(): String {
    return String.format("%.1f", this)
}