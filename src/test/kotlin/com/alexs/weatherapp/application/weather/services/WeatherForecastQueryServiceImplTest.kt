package com.alexs.weatherapp.application.weather.services

import com.alexs.weatherapp.application.common.validation.MetricValidation
import com.alexs.weatherapp.application.weather.queries.GetWeatherForecastByCityAndUnit
import com.alexs.weatherapp.application.weather.repository.WeatherForecastRepository
import com.alexs.weatherapp.domain.weather.errors.MetricsValidationError
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.models.WeatherInfo
import com.alexs.weatherapp.domain.weather.valueObjects.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class WeatherForecastQueryServiceImplTest {

    @Mock
    lateinit var repository: WeatherForecastRepository

    @Mock
    lateinit var metricValidation: MetricValidation

    @InjectMocks
    lateinit var weatherForecastQueryService: WeatherForecastQueryServiceImpl

    private lateinit var query: GetWeatherForecastByCityAndUnit

    @BeforeEach
    fun setUp() {
        query = GetWeatherForecastByCityAndUnit(
            city = "London",
            unit = "metric"
        )
    }

    @Test
    fun `should call metricVerifierClient to verifiy temperature unit`() {
        runBlocking {
            val fakeWeather = provideFakeWeatherForecast()

            `when`(repository.getWeatherForecastByCityName(query.city, query.unit))
                .thenReturn(fakeWeather)

            weatherForecastQueryService.handle(query)

            verify(metricValidation).verifyTemperatureUnit(query.unit)
        }
    }

    @Test
    fun `should throw exception when temperature unit is invalid`() {
        runBlocking {
            `when`(metricValidation.verifyTemperatureUnit(query.unit))
                .thenThrow(MetricsValidationError("Invalid temperature unit: ${query.unit}"))

            val exception = assertThrows<MetricsValidationError> {
                weatherForecastQueryService.handle(query)
            }

            assertEquals("Invalid temperature unit: ${query.unit}", exception.message)
        }
    }

    private fun provideFakeWeatherForecast(): Weather {
        return Weather(
            city = City(
                name = "London",
                coordinate = Coordinate(
                    latitude = 51.5074,
                    longitude = -0.1278
                )
            ),
            weatherInfo = listOf(
                WeatherInfo(
                    date = Instant.now(),
                    temperature = Temperature(
                        min = 15.0,
                        max = 25.0,
                        unit = TemperatureUnit.CELSIUS
                    ),
                    wind = Wind(
                        value = 100.0,
                        unit = WindUnit.KILOMETERS_PER_HOUR
                    ),
                    description = "Sunny",
                )
            )
        )
    }
}