package com.alexs.weatherapp.api.http.weather.controllers

import com.alexs.weatherapp.api.http.weather.contracts.toResponse
import com.alexs.weatherapp.application.weather.queries.GetWeatherForecastByCityAndUnit
import com.alexs.weatherapp.application.weather.services.WeatherForecastQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/weather"])
class WeatherController(
    private val weatherQueryService: WeatherForecastQueryService
) {

    @GetMapping(path = ["/forecast"])
    suspend fun getWeatherForecast(
        @RequestParam(name = "city", required = true) city: String,
        @RequestParam(name = "unit", required = false, defaultValue = "metric") unit: String
    ): ResponseEntity<out Any> {
        val response = weatherQueryService.handle(
            GetWeatherForecastByCityAndUnit(
                city = city,
                unit = unit
            )
        )
        return ResponseEntity.ok(response.toResponse())
    }
}