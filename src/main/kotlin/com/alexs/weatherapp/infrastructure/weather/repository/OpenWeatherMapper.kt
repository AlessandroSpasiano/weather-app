package com.alexs.weatherapp.infrastructure.weather.repository

import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.models.WeatherInfo
import com.alexs.weatherapp.domain.weather.valueObjects.*
import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import java.time.Instant

fun OpenWeatherForecast.toWeather() = Weather(
    city = City(
        name = this.city.name,
        coordinate = Coordinate(
            latitude = city.coord.lat,
            longitude = city.coord.lon
        )
    ),
    weatherInfo = this.list.map {
        WeatherInfo(
            date = Instant.ofEpochSecond(it.dt),
            temperature = Temperature(
                value = it.main.temp,
                unit = TemperatureUnit.CELSIUS
            ),
            wind = Wind(
                value = it.wind.speed,
                unit = WindUnit.KILOMETERS_PER_HOUR
            )
        )
    }
)