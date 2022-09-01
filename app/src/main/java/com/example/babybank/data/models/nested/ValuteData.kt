package com.example.babybank.data.models.nested

import com.google.gson.annotations.SerializedName

data class ValuteData(
    @SerializedName("USD")
    val usdValute: CurrentValute,
    @SerializedName("EUR")
    val eurValute: CurrentValute,
)
