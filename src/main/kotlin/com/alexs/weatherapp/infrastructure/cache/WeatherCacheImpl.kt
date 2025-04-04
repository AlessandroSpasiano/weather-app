package com.alexs.weatherapp.infrastructure.cache

import com.alexs.weatherapp.application.cache.WeatherCache
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.valueObjects.TemperatureUnit
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class WeatherCacheImpl(
    private val redisTemplate: RedisTemplate<String, Weather>
) : WeatherCache {


    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather? {
        val data = redisTemplate.opsForValue()
            .get(getKey(cityName, TemperatureUnit.fromString(temperatureUnit) ?: TemperatureUnit.CELSIUS))
        return data
    }

    override suspend fun putWeatherForecastByCityName(cityName: String, weather: Weather) {
        val key = getKey(
            cityName = cityName,
            temperatureUnit = weather.weatherInfo.firstOrNull()?.temperature?.unit
                ?: TemperatureUnit.CELSIUS
        )
        redisTemplate.opsForValue().set(key, weather)

        redisTemplate.expire(key, Duration.ofDays(1))

    }

    private fun getKey(cityName: String, temperatureUnit: TemperatureUnit): String {
        return "weather:$cityName:${temperatureUnit.name.lowercase()}"
    }
}