package com.example.babybank.data.models

import com.google.gson.annotations.SerializedName

data class PdfLinkDto(
    @SerializedName("url")
    val url: String
)
