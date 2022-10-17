package com.example.profile.presentation.models

import com.example.profile.presentation.intefaces.DisplayableItem

data class PersonalInfoProfileFrgUi(
    val name: String,
    val phoneNumber: String,
    override val idItem: Int = name.hashCode(),
) : DisplayableItem
