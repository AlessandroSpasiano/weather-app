package com.alexs.weatherapp.application.weather.queries

data class GetWeatherForecastByCityAndUnit(
    val city: String,
    val unit: String = "celsius"
): WeatherEventDomainQuery
