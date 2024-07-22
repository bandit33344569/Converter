package com.example.converter.network

import android.util.Log
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    companion object {
        private const val baseUrl = "https://v6.exchangerate-api.com/v6/6e7e2faee3819bb73c971037/"

        fun getHttpClient(): OkHttpClient {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient()
                .newBuilder()
                .addInterceptor(logInterceptor)
                .build()
        }

        fun getHabitServerAPI(client: OkHttpClient): ExchangerateApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ExchangerateApi::class.java)
        }
    }
}

suspend fun <T> retryRequest(delay: Long = 1000, func: suspend () -> T): T {
    while(true) {
        try {
            return func()
        } catch (e: Exception) {
            Log.e("RetryRequest", Thread.currentThread().id.toString(), e)
        }
        delay(delay)
    }
}