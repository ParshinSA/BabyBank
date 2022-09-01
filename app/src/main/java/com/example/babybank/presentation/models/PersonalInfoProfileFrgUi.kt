package com.example.babybank.presentation.models

data class PersonalInfoProfileFrgUi(
    val name: String,
    val avatarLink: String,
    val phoneNumber: String,
    override val idItem: Int = name.hashCode(),
) : DisplayableItem
