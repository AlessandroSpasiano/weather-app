package com.alexs.weatherapp.infrastructure.weather.persistance

import com.alexs.weatherapp.application.weather.persistance.WeatherPersistance
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.valueObjects.TemperatureUnit
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class WeatherPersistanceImpl(
    private val redisTemplate: RedisTemplate<String, Weather>
) : WeatherPersistance {


    override suspend fun getWeatherForecastByCityName(cityName: String, temperatureUnit: String): Weather? {
        return withContext(ctx) {
            log.info("Getting weather forecast for $cityName with unit $temperatureUnit from cache")

            redisTemplate.opsForValue()
                .get(getKey(cityName, TemperatureUnit.fromString(temperatureUnit) ?: TemperatureUnit.CELSIUS))
        }
    }

    override suspend fun saveWeatherForecast(weather: Weather) {
        withContext(ctx) {

            val key = getKey(
                cityName = weather.city.name,
                temperatureUnit = weather.weatherInfo.firstOrNull()?.temperature?.unit
                    ?: TemperatureUnit.CELSIUS
            )
            log.info("Putting weather forecast '$key' in cache")

            redisTemplate.opsForValue().set(key, weather)

            redisTemplate.expire(key, Duration.ofDays(1))
        }

    }

    private fun getKey(cityName: String, temperatureUnit: TemperatureUnit): String {
        return "$CACHE_PREFIX:${cityName.lowercase()}:${temperatureUnit.name.lowercase()}"
    }

    private val ctx = CoroutineName(this::class.java.name) + Dispatchers.IO
    companion object {
        private const val CACHE_PREFIX = "weather"
        private val log = LoggerFactory.getLogger(WeatherPersistanceImpl::class.java)
    }
}