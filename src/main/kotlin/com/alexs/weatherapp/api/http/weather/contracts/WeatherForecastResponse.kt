package com.alexs.weatherapp.api.http.weather.contracts

import com.alexs.weatherapp.api.http.weather.contracts.dto.WeatherInfoResponse
import com.alexs.weatherapp.domain.weather.models.Weather

data class WeatherForecastResponse(
    val city: String,
    val info: List<WeatherInfoResponse>
)

fun Weather.toResponse(): WeatherForecastResponse {
    return WeatherForecastResponse(
        city = city.name,
        info = weatherInfo.map {
            WeatherInfoResponse(
                date = it.date.toString(),
                temperature = "${it.temperature.value.toTemperatureString()} ${it.temperature.unit}",
                wind = "${it.wind.value.toWindString()} km/h"
            )
        }
    )
}

fun Double.toTemperatureString(): String {
    return String.format("%.1f", this)
}

fun Double.toWindString(): String {
    return String.format("%.1f", this)
}