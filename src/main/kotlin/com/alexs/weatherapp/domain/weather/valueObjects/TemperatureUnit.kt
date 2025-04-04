package com.alexs.weatherapp.domain.weather.valueObjects

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    companion object {
        fun fromString(value: String): TemperatureUnit? {
            return when (value.uppercase()) {
                "METRIC" -> CELSIUS
                "IMPERIAL" -> FAHRENHEIT
                "KELVIN" -> KELVIN
                else -> null
            }
        }
    }
}

fun TemperatureUnit.toUnitString(): String {
    return when (this) {
        TemperatureUnit.CELSIUS -> "°C"
        TemperatureUnit.FAHRENHEIT -> "°F"
        TemperatureUnit.KELVIN -> "K"
    }
}