package com.alexs.weatherapp.api.weather.http.controllers

import com.alexs.weatherapp.api.weather.http.contracts.toDateString
import com.alexs.weatherapp.application.weather.queries.GetWeatherForecastByCityAndUnit
import com.alexs.weatherapp.application.weather.services.WeatherForecastQueryService
import com.alexs.weatherapp.domain.weather.errors.WeatherAppCityNotFoundError
import com.alexs.weatherapp.domain.weather.models.Weather
import com.alexs.weatherapp.domain.weather.models.WeatherInfo
import com.alexs.weatherapp.domain.weather.valueObjects.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.Instant

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var weatherQueryService: WeatherForecastQueryService

    @Test
    fun `test getWeatherForecastByCityAndUnit`() {
        runBlocking {
            // Given
            val cityName = "London"
            val temperatureUnit = "Celsius"

            val fake = provideFakeWeatherForecast()
            Mockito.`when`(weatherQueryService.handle(GetWeatherForecastByCityAndUnit(cityName, temperatureUnit)))
                .thenReturn(fake)

            mockMvc.get("/api/v1/weather/forecast?city=$cityName&unit=$temperatureUnit")
                .asyncDispatch()
                .andExpect { status { isOk() } }
                .andExpect { jsonPath("$.city") { value(cityName) } }
                .andExpect {
                    jsonPath("$.info") {
                        this.isArray()
                        this.value(
                            hasSize<WeatherInfo>(1)
                        )
                        jsonPath("$.info[0].date") { value(fake.weatherInfo[0].date.toDateString()) }
                    }
                }
        }
    }

    @Test
    fun `test getWeatherForecastByCityAndUnit 404`() {
        runBlocking {
            // Given
            val cityName = "London"

            Mockito.`when`(weatherQueryService.handle(GetWeatherForecastByCityAndUnit(cityName)))
                .thenThrow(WeatherAppCityNotFoundError(cityName))

            mockMvc.get("/api/v1/weather/forecast?city=$cityName")
                .asyncDispatch()
                .andExpect { status { isNotFound() } }
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