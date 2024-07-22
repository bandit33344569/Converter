package com.example.converter.network

data class ApiResponse(
    val result: String,
    val documentation: String,
    val terms_of_use: String,
    val supported_codes: List<List<String>>
)

