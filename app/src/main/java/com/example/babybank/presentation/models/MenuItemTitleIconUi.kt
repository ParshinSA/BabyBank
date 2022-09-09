package com.example.babybank.presentation.models

import androidx.annotation.DrawableRes
import com.example.babybank.presentation.common.DisplayableItem

data class MenuItemTitleIconUi(
    val title: String,
    @DrawableRes val idIcon: Int,
    override val idItem: Int = idIcon,
) : DisplayableItem
