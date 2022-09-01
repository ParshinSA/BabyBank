package com.example.babybank.presentation.models

data class AccountIconUi(
    val balance: String,
    val currencyTypeIcon: Int,
    override val idItem: Int = currencyTypeIcon,
) : DisplayableItem
