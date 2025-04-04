package com.alexs.weatherapp.api.http.weather.contracts

import com.alexs.weatherapp.api.http.weather.contracts.dto.WeatherInfoResponse
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.valueObjects.toUnitString
import com.alexs.weatherapp.domain.weather.valueObjects.toWindString
import java.time.Instant
import java.time.format.DateTimeFormatter

data class WeatherForecastResponse(
    val city: String,
    val info: List<WeatherInfoResponse>
)

fun Weather.toResponse(): WeatherForecastResponse {
    return WeatherForecastResponse(
        city = city.name,
        info = weatherInfo.map {
            WeatherInfoResponse(
                date = it.date.toDateString(),
                minTemperature = "${it.temperature.min.toTemperatureString()} ${it.temperature.unit.toUnitString()}",
                maxTemperature = "${it.temperature.max.toTemperatureString()} ${it.temperature.unit.toUnitString()}",
                wind = "${it.wind.value.toWindString()} ${it.wind.unit.toWindString()}"
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

fun Instant.toDateString(): String {

    DateTimeFormatter
        .ofPattern("yyyy-MM-dd")
        .withZone(java.time.ZoneOffset.UTC)
        .format(this)
        .let {
            return it
        }
}