package com.alexs.weatherapp.infrastructure.openweather.models

data class OpenWeatherForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherData>,
    val city: OpenCity
)

data class WeatherData(
    val dt: Long,
    val main: Main,
    val weather: List<OpenWeather>,
    val clouds: Clouds,
    val wind: OpenWind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain?,
    val dt_txt: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double
)

data class OpenWeather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(
    val all: Int
)

data class OpenWind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class Rain(
    val `3h`: Double
)

data class OpenCity(
    val id: Int,
    val name: String,
    val coord: OpenCoord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

data class OpenCoord(
    val lat: Double,
    val lon: Double
)
