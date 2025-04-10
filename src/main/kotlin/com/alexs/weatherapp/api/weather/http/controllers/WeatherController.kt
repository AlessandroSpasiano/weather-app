package com.alexs.weatherapp.api.weather.http.controllers

import com.alexs.weatherapp.api.weather.http.contracts.WeatherForecastResponse
import com.alexs.weatherapp.api.weather.http.contracts.toResponse
import com.alexs.weatherapp.api.weather.http.controllerAdvice.ErrorHttpResponse
import com.alexs.weatherapp.application.weather.queries.GetWeatherForecastByCityAndUnit
import com.alexs.weatherapp.application.weather.services.WeatherForecastQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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

    @Operation(
        summary = "Get weather forecast",
        description = "Get weather forecast by city and unit. " +
                "The unit of measurement can be Celsius, Fahrenheit or Kelvin. " +
                "If no unit is specified, the default is Celsius. " +
                "The forecast returned is for the next 5 days.",
        method = "getWeatherForecast",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Weather forecast retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = WeatherForecastResponse::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ErrorHttpResponse::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "City not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = ErrorHttpResponse::class
                        )
                    )
                ]
            )
        ],
        parameters = [
            Parameter(
                name = "city",
                description = "City name",
                required = true,
                `in` = ParameterIn.QUERY,
                schema = Schema(
                    type = "string",
                    example = "London"
                )
            ),
            Parameter(
                name = "unit",
                description = "Unit of measurement",
                required = false,
                `in` = ParameterIn.QUERY,
                schema = Schema(
                    type = "string",
                    example = "celsius"
                )
            )
        ]
    )
    @GetMapping(path = ["/forecast"])
    suspend fun getWeatherForecast(
        @RequestParam(name = "city", required = true) city: String,
        @RequestParam(name = "unit", required = false, defaultValue = "celsius") unit: String
    ): ResponseEntity<WeatherForecastResponse> {
        val response = weatherQueryService.handle(
            GetWeatherForecastByCityAndUnit(
                city = city,
                unit = unit
            )
        )
        return ResponseEntity.ok(response.toResponse())
    }
}