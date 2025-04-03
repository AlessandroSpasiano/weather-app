package com.alexs.weatherapp.domain.weather.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class WeatherAppError(msg: String): RuntimeException(msg)

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class MetricsValidationError(msg: String): WeatherAppError(msg)