package com.example.babybank.presentation.models

import androidx.annotation.StringRes

data class MenuTitleUi(
    @StringRes val title: Int,
    override val idItem: Int = title.hashCode()
) : DisplayableItem
