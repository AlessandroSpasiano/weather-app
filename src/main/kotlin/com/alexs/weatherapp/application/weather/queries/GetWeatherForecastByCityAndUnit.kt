package com.alexs.weatherapp.application.weather.queries


// TODO: forse si possono usare gli oggetti `City`?
data class GetWeatherForecastByCityAndUnit(
    val city: String,
    val unit: String = "metric"
): WeatherEventDomainQuery
