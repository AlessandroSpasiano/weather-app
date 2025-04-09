package com.alexs.weatherapp.infrastructure.weather.validation

import com.alexs.weatherapp.application.common.validation.MetricValidation
import com.alexs.weatherapp.domain.weather.errors.MetricsValidationError
import com.alexs.weatherapp.domain.weather.valueObjects.TemperatureUnit
import org.springframework.stereotype.Component

@Component
class MetricValidationImpl: MetricValidation {
    override fun verifyTemperatureUnit(temperatureUnit: String): Boolean {
        if (TemperatureUnit.fromString(temperatureUnit) == null) {
            throw MetricsValidationError(
                "Invalid temperature unit: $temperatureUnit. Valid options are: ${TemperatureUnit.entries.joinToString(", ") { it.name.lowercase() }}."

            )
        }

        return true
    }

}