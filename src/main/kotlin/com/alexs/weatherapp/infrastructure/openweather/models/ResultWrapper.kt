package com.alexs.weatherapp.infrastructure.openweather.models

import com.alexs.weatherapp.infrastructure.openweather.utils.ErrorResponse


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}