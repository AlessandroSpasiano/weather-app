package com.alexs.weatherapp.infrastructure.configuration.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.io.IOException
import java.lang.String


internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s",
                request.url
            )
        )

        val response: Response = chain.proceed(request)

        val t2 = System.nanoTime()
        logger.info(
            String.format(
                "Received response for %s in %.1fms%n",
                response.request.url, (t2 - t1) / 1e6
            )
        )

        return response
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)
    }
}