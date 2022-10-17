package com.example.profile.presentation.models

import androidx.annotation.StringRes
import com.example.profile.presentation.intefaces.DisplayableItem

data class MenuTitleUi(
    @StringRes val title: Int,
    override val idItem: Int = title.hashCode()
) : DisplayableItem
