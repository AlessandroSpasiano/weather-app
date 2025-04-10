package com.alexs.weatherapp.infrastructure.configuration.openweather

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "openweather")
class OpenWeatherConfiguration {
    var apiKey: String = ""
    var url: String = ""
}