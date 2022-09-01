package com.example.babybank.presentation.models

data class PersonalInfoHomeFrgUi(
    val name: String,
    override val idItem: Int = name.hashCode()
) : DisplayableItem
