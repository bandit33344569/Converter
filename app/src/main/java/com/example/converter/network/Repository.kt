package com.example.converter.network

import com.example.converter.model.Currency

class Repository : IRepository {
    private val api = NetworkClient.getHabitServerAPI(NetworkClient.getHttpClient())
    override suspend fun getSupportedCodes(): List<Currency> {
        val response = retryRequest { api.getCodes() }
        return response.supported_codes.map { Currency(it[0], it[1]) }
    }

    override suspend fun getCurrencies(fromCurrencyCode: String): Map<String,Float> {
        val response = retryRequest { api.getCurrencies(fromCurrencyCode) }

        return response.conversion_rates
        }
    }
