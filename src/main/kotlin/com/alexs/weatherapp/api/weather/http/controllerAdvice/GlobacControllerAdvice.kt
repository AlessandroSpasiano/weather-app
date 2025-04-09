package com.alexs.weatherapp.api.weather.http.controllerAdvice

import com.alexs.weatherapp.domain.weather.errors.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant

@ControllerAdvice
class GlobacControllerAdvice {

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun handleMissingServletRequestParameterException(ex: MissingServletRequestParameterException): ResponseEntity<ErrorHttpResponse> {
        val errorHttpResponse = ErrorHttpResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Request parameter '${ex.parameterName}' is missing",
            Instant.now().toString()
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorHttpResponse)
            .also { logError(ex) }
    }

    @ExceptionHandler(value = [Exception::class, WeatherAppInternalError::class])
    fun handleInternalServerError(ex: Exception): ResponseEntity<ErrorHttpResponse> {
        val errorHttpResponse = ErrorHttpResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.message ?: "",
            Instant.now().toString()
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorHttpResponse)
            .also { logError(ex) }
    }

    private fun logError(ex: Exception) {
        log.error("(GlobalControllerAdvice) ${ex::class.java.name}: ${ex.stackTraceToString()}")
    }

    @ExceptionHandler(value = [WeatherAppCityNotFoundError::class])
    fun handleWeatherAppCityNotFoundError(ex: WeatherAppCityNotFoundError): ResponseEntity<ErrorHttpResponse> {
        val errorHttpResponse = ErrorHttpResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.message ?: "",
            Instant.now().toString()
        )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorHttpResponse)
            .also { logError(ex) }
    }

    @ExceptionHandler(value = [WeatherAppUnauthorizedError::class])
    fun handleWeatherAppUnauthorizedError(ex: WeatherAppUnauthorizedError): ResponseEntity<ErrorHttpResponse> {
        val errorHttpResponse = ErrorHttpResponse(
            HttpStatus.UNAUTHORIZED.value(),
            ex.message ?: "",
            Instant.now().toString()
        )

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorHttpResponse)
            .also { logError(ex) }
    }

    @ExceptionHandler(value = [MetricsValidationError::class, WeatherInvalidCoordinate::class])
    fun handleBadRequestError(ex: WeatherAppError): ResponseEntity<ErrorHttpResponse> {
        val errorHttpResponse = ErrorHttpResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message ?: "",
            Instant.now().toString()
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorHttpResponse)
            .also { logError(ex) }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GlobacControllerAdvice::class.java)
    }
}