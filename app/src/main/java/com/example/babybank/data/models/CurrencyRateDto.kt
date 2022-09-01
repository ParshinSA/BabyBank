package com.example.babybank.data.models

import com.example.babybank.data.models.nested.ValuteData
import com.google.gson.annotations.SerializedName

data class CurrencyRateDto(
    @SerializedName("Valute")
    val valuteData: ValuteData
)
