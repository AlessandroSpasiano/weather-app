package com.alexs.weatherapp.infrastructure.configuration.cache

import com.alexs.weatherapp.domain.weather.models.Weather
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class CacheConfig {

    @Bean
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(JavaTimeModule())
            .registerModules(Jdk8Module())
            .registerKotlinModule()
    }


    @Bean
    fun redisTemplate(connection: RedisConnectionFactory, objectMapper: ObjectMapper): RedisTemplate<String, Weather> {
        val template = RedisTemplate<String, Weather>()
        template.connectionFactory = connection
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(objectMapper,Weather::class.java)
        template.afterPropertiesSet()
        return template
    }
}