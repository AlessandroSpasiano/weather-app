package com.alexs.weatherapp.api.configuration.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun api(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Weather API")
                    .version("1.0.0")
                    .description("API for weather data")
            )
            .servers(
                listOf(
                    Server().url("http://localhost:8080").description("Local server")
                )
            )
    }
}