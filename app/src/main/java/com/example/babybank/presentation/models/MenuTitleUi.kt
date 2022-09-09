package com.example.babybank.presentation.models

import androidx.annotation.StringRes
import com.example.babybank.presentation.common.DisplayableItem

data class MenuTitleUi(
    @StringRes val title: Int,
    override val idItem: Int = title.hashCode()
) : DisplayableItem
