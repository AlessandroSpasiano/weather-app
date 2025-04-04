package com.alexs.weatherapp.domain.weather.valueObjects

enum class WindUnit {
    KILOMETERS_PER_HOUR
}

fun WindUnit.toWindString(): String {
    return when (this) {
        WindUnit.KILOMETERS_PER_HOUR -> "km/h"
    }
}