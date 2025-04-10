package com.alexs.weatherapp.infrastructure.weather.repository

import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.models.WeatherInfo
import com.alexs.weatherapp.domain.weather.valueObjects.*
import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import java.time.Instant
import java.time.OffsetDateTime

fun OpenWeatherForecast.toWeather(temperatureUnit: String) = Weather(
    city = City(
        name = this.city.name,
        coordinate = Coordinate(
            latitude = city.coord.lat,
            longitude = city.coord.lon
        )
    ),
    weatherInfo = this.list
        .groupBy {
            OffsetDateTime.ofInstant(
                Instant.ofEpochSecond(it.dt),
                java.time.ZoneOffset.UTC
            ).toLocalDate()
        }
        .map { it.value.first() }
        .map {
            WeatherInfo(
                date = Instant.ofEpochSecond(it.dt),
                temperature = Temperature(
                    min = it.main.temp_min,
                    max = it.main.temp_max,
                    unit = TemperatureUnit.fromString(temperatureUnit) ?: TemperatureUnit.CELSIUS
                ),
                wind = Wind(
                    value = it.wind.speed,
                    unit = WindUnit.KILOMETERS_PER_HOUR
                ),
                description = it.weather.firstOrNull()?.description ?: "No description available"
            )
    }
)

fun String.toOpenWeatherUnit(): String {
    return when (this) {
        TemperatureUnit.CELSIUS.toUnitString() -> "metric"
        TemperatureUnit.FAHRENHEIT.toUnitString() -> "imperial"
        TemperatureUnit.KELVIN.toUnitString() -> "standard"
        else -> "metric"
    }
}