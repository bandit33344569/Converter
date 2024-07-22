package com.example.converter.network

import com.example.converter.model.Currency

interface IRepository {
    suspend fun getSupportedCodes(): List<Currency>
    suspend fun getCurrencies(fromCurrencyCode: String): Map<String, Float>
}