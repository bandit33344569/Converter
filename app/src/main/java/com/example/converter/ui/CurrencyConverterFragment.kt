package com.example.converter.ui

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.converter.R
import com.example.converter.model.Currency
import com.example.converter.model.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyConverterFragment : Fragment() {
    private var callback: CurrencyConverterCallback? = null

    interface CurrencyConverterCallback {
        fun onConvertButtonClicked(amount: Float, fromCurrency: String, toCurrency: String)
    }

    private lateinit var spinnerCurrencyFrom: Spinner
    private lateinit var spinnerCurrencyTo: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var buttonConvert: Button
    private var currencyList: List<Currency>? = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CurrencyConverterCallback) {
            callback = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.currency_converter_fragment, container, false)
        loadCurrencies()

        spinnerCurrencyFrom = view.findViewById(R.id.spinnerCurrencyFrom)
        spinnerCurrencyTo = view.findViewById(R.id.spinnerCurrencyTo)
        editTextAmount = view.findViewById(R.id.editTextAmount)
        buttonConvert = view.findViewById(R.id.buttonConvert)

        setButtonConvertClickListener()
        editTextAmount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        return view
    }

    private fun setButtonConvertClickListener(){
        buttonConvert.setOnClickListener {
            val amountString = editTextAmount.text.toString()
            if (amountString.isNotEmpty()) {
                val amount = amountString.toFloat()
                val fromCurrency = spinnerCurrencyFrom.selectedItem as Currency
                val toCurrency = spinnerCurrencyTo.selectedItem as Currency
                callback?.onConvertButtonClicked(amount, fromCurrency.code, toCurrency.code)
            } else {
                Toast.makeText(requireContext(), "Please enter an amount.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCurrencies() {
        lifecycleScope.launch(Dispatchers.IO) {
            currencyList = UseCase.loadSupportedCodes()
            currencyList?.let {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                withContext(Dispatchers.Main) {
                    spinnerCurrencyFrom.adapter = adapter
                    spinnerCurrencyTo.adapter = adapter
                }
            }
        }
    }

}
