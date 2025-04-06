package com.alexs.weatherapp.infrastructure.openweather.repository

import com.alexs.weatherapp.application.openweather.repository.OpenWeatherRepository
import com.alexs.weatherapp.infrastructure.openweather.OpenWeatherService
import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import com.alexs.weatherapp.infrastructure.openweather.utils.safeApiCall
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class OpenWeatherRepositoryImpl(
    private val openWeatherService: OpenWeatherService
): OpenWeatherRepository {

    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): ResultWrapper<OpenWeatherForecast> {
        return safeApiCall(ctx) {
            log.info("Fetching weather forecast for city: $cityName with unit: $temperatureUnit from OpenWeather API")
            openWeatherService.getWeatherForecast(cityName, temperatureUnit)
        }
    }

    private val ctx = CoroutineName(this::class.java.name) + Dispatchers.IO

    companion object {
        private val log = LoggerFactory.getLogger(OpenWeatherRepositoryImpl::class.java)
    }

}