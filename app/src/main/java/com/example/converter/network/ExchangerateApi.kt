package com.example.converter.network

import com.example.converter.model.Currency
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangerateApi {
    @GET("latest/{baseCurrency}")
    suspend fun getCurrencies(
        @Path("baseCurrency") baseCurrency: String
    ): CurrencyResponse

    @GET("codes")
    suspend fun getCodes(): ApiResponse
}