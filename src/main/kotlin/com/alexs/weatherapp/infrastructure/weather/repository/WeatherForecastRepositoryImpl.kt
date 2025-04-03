package com.alexs.weatherapp.infrastructure.weather.repository

import com.alexs.weatherapp.application.weather.repository.WeatherForecastRepository
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.infrastructure.openweather.OpenWeatherService
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository


@Repository
class WeatherForecastRepositoryImpl(
    private val openWeatherService: OpenWeatherService
): WeatherForecastRepository {

    private val ctx = Job() + CoroutineName(this::class.java.name) + Dispatchers.IO
    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather {
        return withContext(ctx) {
            log.info("Fetching weather forecast for city: $cityName with unit: $temperatureUnit")

            openWeatherService.getWeatherForecast(cityName, temperatureUnit)
                .toWeather()
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(WeatherForecastRepositoryImpl::class.java)
    }
}