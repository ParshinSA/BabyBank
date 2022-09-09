package com.example.babybank.presentation.models

import com.example.babybank.presentation.common.DisplayableItem

data class PersonalInfoHomeFrgUi(
    val name: String,
    override val idItem: Int = name.hashCode()
) : DisplayableItem
