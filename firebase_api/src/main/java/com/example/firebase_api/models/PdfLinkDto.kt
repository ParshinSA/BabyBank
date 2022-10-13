package com.example.firebase_api.models

import com.google.gson.annotations.SerializedName

data class PdfLinkDto(
    @SerializedName("url")
    val url: String
)
