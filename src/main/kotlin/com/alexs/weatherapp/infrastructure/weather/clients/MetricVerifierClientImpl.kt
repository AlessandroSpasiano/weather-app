package com.alexs.weatherapp.infrastructure.weather.clients

import com.alexs.weatherapp.application.common.clients.MetricVerifierClient
import com.alexs.weatherapp.domain.weather.errors.MetricsValidationError
import com.alexs.weatherapp.domain.weather.valueObjects.TemperatureUnit
import org.springframework.stereotype.Component

@Component
class MetricVerifierClientImpl: MetricVerifierClient {
    override fun verifyTemperatureUnit(temperatureUnit: String): Boolean {
        if (TemperatureUnit.fromString(temperatureUnit) == null) {
            throw MetricsValidationError("Invalid temperature unit: $temperatureUnit")
        }

        return true
    }

}