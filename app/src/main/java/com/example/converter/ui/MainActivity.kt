package com.example.converter.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.converter.R
import com.example.converter.model.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), CurrencyConverterFragment.CurrencyConverterCallback,
    ConvertedCurrencyFragment.ConvertedCurrencyCallBack {

    private lateinit var navController: NavController


    private suspend fun loadPrices(fromCurrency: String): Map<String, Float> {
        return withContext(Dispatchers.IO) {
            UseCase.getPricesFromCurrency(fromCurrency)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onConvertButtonClicked(amount: Float, fromCurrency: String, toCurrency: String) {
        lifecycleScope.launch {
            val prices = loadPrices(fromCurrency)
            val convertedAmount = (prices[toCurrency]?.times(amount)) ?: 0.0
            val action =
                CurrencyConverterFragmentDirections.actionCurrencyConverterFragmentToConvertedCurrencyFragment(
                    convertedAmount = convertedAmount as Float,
                    convertedCurrencyCode = toCurrency
                )
            navController.navigate(action)
        }
    }

    override fun onBackButtonClicked() {
        val action = ConvertedCurrencyFragmentDirections.actionConvertedCurrencyFragmentToHome()
        navController.navigate(action)
    }


}