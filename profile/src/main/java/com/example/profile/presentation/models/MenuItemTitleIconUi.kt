package com.example.profile.presentation.models

import androidx.annotation.DrawableRes
import com.example.profile.presentation.intefaces.DisplayableItem

data class MenuItemTitleIconUi(
    val title: String,
    @DrawableRes val idIcon: Int,
    override val idItem: Int = idIcon,
) : DisplayableItem
