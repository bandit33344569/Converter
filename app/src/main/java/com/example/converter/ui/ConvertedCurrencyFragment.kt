package com.example.converter.ui

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.converter.R
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.navArgs
import com.example.converter.ui.ConvertedCurrencyFragmentArgs

class ConvertedCurrencyFragment: Fragment() {
    private var callback: ConvertedCurrencyCallBack? = null
    private val args: ConvertedCurrencyFragmentArgs  by navArgs()

    interface ConvertedCurrencyCallBack {
        fun onBackButtonClicked()
    }

    private lateinit var convertedValueTextView: TextView
    private lateinit var currencyCodeTextView: TextView
    private lateinit var backButton: Button


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ConvertedCurrencyCallBack) {
            callback = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.converted_currency_fragment, container, false)


        convertedValueTextView = view.findViewById(R.id.ConvertedValueText)
        currencyCodeTextView = view.findViewById(R.id.CurrenceToConverted)
        backButton = view.findViewById(R.id.buttonBack)

        convertedValueTextView.text = args.convertedAmount.toString()
        currencyCodeTextView.text = args.convertedCurrencyCode

        backButton.setOnClickListener {
            callback?.onBackButtonClicked()
        }

        return view
    }


}