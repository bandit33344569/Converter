package com.example.converter.model

data class Currency(val code: String, val name: String){
    override fun toString(): String {
        return code
    }
}