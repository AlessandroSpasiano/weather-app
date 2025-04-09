package com.alexs.weatherapp.application.common.validation

interface MetricValidation {

    fun verifyTemperatureUnit(
        temperatureUnit: String
    ): Boolean
}