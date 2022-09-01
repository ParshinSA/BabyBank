package com.example.babybank.data.models.nested

import com.google.gson.annotations.SerializedName

data class CurrentValute(
    @SerializedName("Value")
    val value: Double
)