package com.example.converter.network

data class CurrencyResponse(
    val base_code: String,
    val conversion_rates: Map<String, Float>
)