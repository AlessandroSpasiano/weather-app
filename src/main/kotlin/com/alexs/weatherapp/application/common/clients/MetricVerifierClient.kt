package com.alexs.weatherapp.application.common.clients

interface MetricVerifierClient {

    fun verifyTemperatureUnit(
        temperatureUnit: String
    ): Boolean
}