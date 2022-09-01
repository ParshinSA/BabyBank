package com.example.babybank.presentation.models

data class CardUi(
    val shortId: String,
    val balance: String,
    override val idItem: Int = shortId.toInt()
) : DisplayableItem