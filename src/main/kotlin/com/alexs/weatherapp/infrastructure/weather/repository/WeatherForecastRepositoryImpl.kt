package com.alexs.weatherapp.infrastructure.weather.repository

import com.alexs.weatherapp.application.openweather.repository.OpenWeatherRepository
import com.alexs.weatherapp.application.weather.repository.WeatherForecastRepository
import com.alexs.weatherapp.domain.weather.errors.WeatherAppInternalError
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.infrastructure.openweather.OpenWeatherService
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository


@Repository
class WeatherForecastRepositoryImpl(
    private val openWeatherRepository: OpenWeatherRepository
): WeatherForecastRepository {

    private val ctx = Job() + CoroutineName(this::class.java.name) + Dispatchers.IO
    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather {
        return withContext(ctx) {
            log.info("Fetching weather forecast for city: $cityName with unit: $temperatureUnit")

            val response =
                openWeatherRepository.getWeatherForecastByCityName(cityName, temperatureUnit)

            when (response) {
                is ResultWrapper.Success -> {
                    log.info("Weather forecast fetched successfully")
                    response.value.toWeather()
                }
                is ResultWrapper.GenericError -> {
                    log.error("Error fetching weather forecast: ${response.code} - ${response.error}")
                    throw WeatherAppInternalError("Error fetching weather forecast")
                }
                is ResultWrapper.NetworkError -> {
                    log.error("Network error fetching weather forecast")
                    throw WeatherAppInternalError("Network error fetching weather forecast")
                }
            }
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(WeatherForecastRepositoryImpl::class.java)
    }
}