package com.alexs.weatherapp.infrastructure.openweather.repository

import com.alexs.weatherapp.application.openweather.repository.OpenWeatherRepository
import com.alexs.weatherapp.infrastructure.openweather.OpenWeatherService
import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import com.alexs.weatherapp.infrastructure.openweather.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository

@Repository
class OpenWeatherRepositoryImpl(
    private val openWeatherService: OpenWeatherService
): OpenWeatherRepository {
    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): ResultWrapper<OpenWeatherForecast> {
        return safeApiCall(Dispatchers.IO) {
            openWeatherService.getWeatherForecast(cityName, temperatureUnit)
        }
    }

}