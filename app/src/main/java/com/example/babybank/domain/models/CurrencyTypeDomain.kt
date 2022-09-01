package com.example.babybank.domain.models

import androidx.annotation.IntegerRes
import com.example.babybank.R

enum class CurrencyTypeDomain(
    val symbol: String,
    @IntegerRes val icon: Int
) {
    EUR("€", R.drawable.ic_dollar),
    USD("\$", R.drawable.ic_euro),
    RUB("₽", 0)
}