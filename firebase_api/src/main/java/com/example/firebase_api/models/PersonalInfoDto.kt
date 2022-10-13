package com.example.firebase_api.models

import com.google.gson.annotations.SerializedName

data class PersonalInfoDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("middlename")
    val middleName: String?,
    @SerializedName("lastname")
    val lastName: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?
)