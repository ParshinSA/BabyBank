package com.example.babybank.data.models

import com.example.babybank.data.models.nested.CardDto
import com.google.gson.annotations.SerializedName

data class AccountInfoDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("balance")
    val balance: Double?,
    @SerializedName("currency_type")
    val currencyType: String?,
    @SerializedName("cards")
    val cards: List<CardDto>? = emptyList()
)
