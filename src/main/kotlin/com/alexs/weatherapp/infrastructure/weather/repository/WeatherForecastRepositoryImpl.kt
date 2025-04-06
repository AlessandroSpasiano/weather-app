package com.alexs.weatherapp.infrastructure.weather.repository

import com.alexs.weatherapp.application.openweather.repository.OpenWeatherRepository
import com.alexs.weatherapp.application.weather.repository.WeatherForecastRepository
import com.alexs.weatherapp.domain.weather.errors.WeatherAppCityNotFoundError
import com.alexs.weatherapp.domain.weather.errors.WeatherAppInternalError
import com.alexs.weatherapp.domain.weather.errors.WeatherAppUnauthorizedError
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class WeatherForecastRepositoryImpl(
    private val openWeatherRepository: OpenWeatherRepository
) : WeatherForecastRepository {

    private val ctx = CoroutineName(this::class.java.name) + Dispatchers.IO
    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather {
        return withContext(ctx) {
            log.info("Fetching weather forecast for city: $cityName with unit: $temperatureUnit")

            val response =
                openWeatherRepository.getWeatherForecastByCityName(cityName, temperatureUnit.toOpenWeatherUnit())

            when (response) {
                is ResultWrapper.Success -> {
                    log.info("Weather forecast fetched successfully")
                    response.value.toWeather(temperatureUnit)
                }

                is ResultWrapper.GenericError -> {
                    log.error("Error fetching weather forecast: ${response.code} - ${response.error}")
                    handleError(response)
                }

                is ResultWrapper.NetworkError -> {
                    log.error("Network error fetching weather forecast")
                    throw WeatherAppInternalError("Network error fetching weather forecast")
                }
            }
        }
    }

    private fun handleError(
        error: ResultWrapper.GenericError,
        defaultMessage: String = "Error fetching weather forecast"
    ): Weather {
        log.error("Error fetching weather forecast: ${error.code} - ${error.error}")
        when (error.code) {
            400 -> throw WeatherAppInternalError("Bad request: ${error.error}")
            404 -> throw WeatherAppCityNotFoundError(error.error?.message.orEmpty().ifEmpty { "City not found" })
            401 -> throw WeatherAppUnauthorizedError("Unauthorized")
            else -> throw WeatherAppInternalError(defaultMessage)
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(WeatherForecastRepositoryImpl::class.java)
    }
}