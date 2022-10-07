package com.example.babybank.presentation.models

import com.example.babybank.presentation.common.DisplayableItem

data class PersonalInfoProfileFrgUi(
    val name: String,
    val phoneNumber: String,
    override val idItem: Int = name.hashCode(),
) : DisplayableItem
