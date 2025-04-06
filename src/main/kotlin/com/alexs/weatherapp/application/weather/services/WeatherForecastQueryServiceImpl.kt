package com.alexs.weatherapp.application.weather.services

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
    private val metricVerifierClient: MetricVerifierClient
): WeatherForecastQueryService {
    override suspend fun handle(
        query: GetWeatherForecastByCityAndUnit
    ): Weather {
        return withContext(ctx) {

            log.info("Executing GET weather for ${query.city} with unit ${query.unit}")

            metricVerifierClient.verifyTemperatureUnit(query.unit)

            weatherForecastRepository.getWeatherForecastByCityName(
                cityName = query.city,
                temperatureUnit = query.unit
            )
        }
    }

    private val ctx = CoroutineName(this::class.java.name) + Dispatchers.IO

    companion object {
        private val log = LoggerFactory.getLogger(WeatherForecastQueryServiceImpl::class.java)
    }
}