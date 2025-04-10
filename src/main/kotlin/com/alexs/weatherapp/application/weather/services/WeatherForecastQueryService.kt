package com.alexs.weatherapp.application.weather.services

import com.alexs.weatherapp.application.weather.queries.GetWeatherForecastByCityAndUnit
import com.alexs.weatherapp.domain.weather.models.Weather

interface WeatherForecastQueryService {

    suspend fun handle(
        query: GetWeatherForecastByCityAndUnit
    ): Weather
}