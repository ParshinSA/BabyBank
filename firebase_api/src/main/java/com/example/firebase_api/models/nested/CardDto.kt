package com.example.firebase_api.models.nested

import com.google.gson.annotations.SerializedName

data class CardDto(
    @SerializedName("id")
    val id: Long?,
)
