package com.alexs.weatherapp.application.weather.services

import com.alexs.weatherapp.application.cache.WeatherCache
import com.alexs.weatherapp.application.common.clients.MetricVerifierClient
import com.alexs.weatherapp.application.weather.queries.GetWeatherForecastByCityAndUnit
import com.alexs.weatherapp.application.weather.repository.WeatherForecastRepository
import com.alexs.weatherapp.domain.weather.models.Weather
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WeatherForecastQueryServiceImpl(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val metricVerifierClient: MetricVerifierClient,
    private val weatherCache: WeatherCache
): WeatherForecastQueryService {
    override suspend fun handle(
        query: GetWeatherForecastByCityAndUnit
    ): Weather {
        return withContext(ctx) {

            log.info("Executing GET weather for ${query.city} with unit ${query.unit}")

            metricVerifierClient.verifyTemperatureUnit(query.unit)

            weatherCache.getWeatherForecastByCityName(query.city, query.unit)
                ?: run {
                    log.info("Weather forecast not found in cache, fetching from repository")

                    weatherForecastRepository.getWeatherForecastByCityName(
                        cityName = query.city,
                        temperatureUnit = query.unit
                    ).also {

                        log.info("Weather forecast fetched from repository. Caching it.")

                        weatherCache.putWeatherForecastByCityName(
                            cityName = query.city,
                            weather = it
                        )
                    }
                }
        }
    }

    private val ctx = CoroutineName(this::class.java.name) + Dispatchers.IO

    companion object {
        private val log = LoggerFactory.getLogger(WeatherForecastQueryServiceImpl::class.java)
    }
}