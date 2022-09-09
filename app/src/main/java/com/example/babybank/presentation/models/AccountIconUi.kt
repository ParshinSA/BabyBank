package com.example.babybank.presentation.models

import com.example.babybank.presentation.common.DisplayableItem

data class AccountIconUi(
    val balance: String,
    val currencyTypeIcon: Int,
    override val idItem: Int = currencyTypeIcon,
) : DisplayableItem
