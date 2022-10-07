package com.example.babybank.domain.models

data class PersonalInfoDomain(
    val id: Long,
    val name: String,
    val middleName: String,
    val lastName: String,
    val phoneNumber: String
)