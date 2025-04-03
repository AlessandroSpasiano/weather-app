package com.alexs.weatherapp.infrastructure.configuration.retrofit

import com.alexs.weatherapp.infrastructure.configuration.interceptor.LoggingInterceptor
import com.alexs.weatherapp.infrastructure.configuration.openweather.OpenWeatherConfiguration
import com.alexs.weatherapp.infrastructure.openweather.OpenWeatherService
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class HttpConfig {

    @Bean
    fun provideOkHttpClient(
        openWeatherConfiguration: OpenWeatherConfiguration
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()

                val newUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("appid", openWeatherConfiguration.apiKey)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    @Bean
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        openWeatherConfiguration: OpenWeatherConfiguration): OpenWeatherService {
        return Retrofit.Builder()
            .baseUrl(openWeatherConfiguration.url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherService::class.java)
    }
}