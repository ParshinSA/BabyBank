package com.example.babybank.presentation.models

import com.example.babybank.presentation.common.DisplayableItem

data class CardUi(
    val shortId: String,
    val balance: String,
    override val idItem: Int = shortId.toInt()
) : DisplayableItem