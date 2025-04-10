package com.alexs.weatherapp.infrastructure.openweather.utils

import com.alexs.weatherapp.domain.weather.errors.MetricsValidationError
import com.alexs.weatherapp.domain.weather.errors.WeatherAppCityNotFoundError
import com.alexs.weatherapp.domain.weather.errors.WeatherAppInternalError
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

suspend fun <T> safeApiCall(dispatcher: CoroutineContext, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.NetworkError
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val errorBody = it.readUtf8()
            Gson().fromJson(errorBody, ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}

data class ErrorResponse(val cod: Int, val message: String?)
