package com.example.babybank.data.models

import com.google.gson.annotations.SerializedName

data class MenuItemDto(
    @SerializedName("title")
    val title: String?,
    @SerializedName("icon_id")
    val idIcon: String?
)
