package com.example.babybank.domain.models

data class AccountInfoDomain(
    val id: Long,
    val balance: Double,
    val currencyType: String,
    val cards: List<CardDomain> = emptyList()
)
