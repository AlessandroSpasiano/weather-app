package com.alexs.weatherapp.infrastructure.weather.repository

import com.alexs.weatherapp.application.openweather.repository.OpenWeatherRepository
import com.alexs.weatherapp.application.weather.persistance.WeatherPersistance
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.models.WeatherInfo
import com.alexs.weatherapp.domain.weather.valueObjects.*
import com.alexs.weatherapp.infrastructure.openweather.models.OpenCity
import com.alexs.weatherapp.infrastructure.openweather.models.OpenCoord
import com.alexs.weatherapp.infrastructure.openweather.models.OpenWeatherForecast
import com.alexs.weatherapp.infrastructure.openweather.models.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class WeatherForecastRepositoryImplTest {

    @Mock
    lateinit var openWeatherRepository: OpenWeatherRepository

    @Mock
    lateinit var weatherPersistance: WeatherPersistance

    @InjectMocks
    lateinit var weatherForecastRepository: WeatherForecastRepositoryImpl

    @Test
    fun `should fetch weather from cache if available`() {
        runBlocking {
            val cityName = "London"
            val temperatureUnit = "metric"
            val weather = provideFakeWeatherForecast()

            `when`(weatherPersistance.getWeatherForecastByCityName(cityName, temperatureUnit)).thenReturn(weather)

            val result = weatherForecastRepository.getWeatherForecastByCityName(cityName, temperatureUnit)

            assertEquals(weather, result)
            verify(weatherPersistance).getWeatherForecastByCityName(cityName, temperatureUnit)
            verifyNoMoreInteractions(openWeatherRepository)
        }
    }

    @Test
    fun `should fetch weather from repository if not in cache`() {
        runBlocking {
            val cityName = "London"
            val temperatureUnit = "metric"
            val openWeather = provideFakeOpenWeatherResponse()
            val weather = openWeather.toWeather("metric")

            `when`(weatherPersistance.getWeatherForecastByCityName(cityName, temperatureUnit)).thenReturn(null)
            `when`(openWeatherRepository.getWeatherForecastByCityName(cityName, temperatureUnit)).thenReturn(
                ResultWrapper.Success<OpenWeatherForecast>(openWeather)
            )

            val result = weatherForecastRepository.getWeatherForecastByCityName(cityName, temperatureUnit)

            assertEquals(weather, result)
            verify(weatherPersistance).getWeatherForecastByCityName(cityName, temperatureUnit)
            verify(openWeatherRepository).getWeatherForecastByCityName(cityName, temperatureUnit)
        }
    }

    @Test
    fun `should put weather in cache after fetching from repository`() {
        runBlocking {
            val cityName = "London"
            val temperatureUnit = "metric"
            val openWeather = provideFakeOpenWeatherResponse()
            val weather = openWeather.toWeather("metric")

            `when`(weatherPersistance.getWeatherForecastByCityName(cityName, temperatureUnit)).thenReturn(null)
            `when`(openWeatherRepository.getWeatherForecastByCityName(cityName, temperatureUnit)).thenReturn(
                ResultWrapper.Success<OpenWeatherForecast>(openWeather)
            )

            weatherForecastRepository.getWeatherForecastByCityName(cityName, temperatureUnit)

            verify(weatherPersistance).saveWeatherForecast(weather)
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

    private fun provideFakeOpenWeatherResponse(): OpenWeatherForecast {
        return OpenWeatherForecast(
            cod = "200",
            message = 0,
            cnt = 40,
            city = OpenCity(
                id = 1,
                name = "London",
                coord = OpenCoord(
                    lat = 51.5074,
                    lon = -0.1278
                ),
                country = "London",
                population = 0,
                timezone = 0,
                sunrise = 0,
                sunset = 0
            ),
            list = listOf(),
        )
    }
}