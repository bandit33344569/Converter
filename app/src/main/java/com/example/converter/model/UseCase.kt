package com.example.converter.model

import com.example.converter.network.IRepository
import com.example.converter.network.Repository

class UseCase {
    companion object {
        private val repository: IRepository = Repository()

        suspend fun loadSupportedCodes(): List<Currency> {
            return repository.getSupportedCodes()
        }

        suspend fun getPricesFromCurrency(fromCurrencyCode: String): Map<String,Float> {
            return repository.getCurrencies(fromCurrencyCode)
        }
    }

}