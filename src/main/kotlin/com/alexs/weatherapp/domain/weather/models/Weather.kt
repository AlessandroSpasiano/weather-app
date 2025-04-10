package com.alexs.weatherapp.domain.weather.models

import com.alexs.weatherapp.domain.weather.errors.WeatherInvalidCoordinate
import com.alexs.weatherapp.domain.weather.valueObjects.City
import java.io.Serializable


class Weather: Serializable {

    var city: City = City()
        private set
    var weatherInfo: List<WeatherInfo> = emptyList()
        private set

    constructor(city: City, weatherInfo: List<WeatherInfo>) {
        val coordinate = city.coordinate
        if (coordinate.latitude !in -90.0..90.0) {
            throw WeatherInvalidCoordinate("Latitude must be between -90 and 90")
        }

        if (coordinate.longitude !in -180.0..180.0) {
            throw WeatherInvalidCoordinate("Longitude must be between -180 and 180")
        }

        this.city = city
        this.weatherInfo = weatherInfo
    }
}