package com.example.babybank.domain.models

data class PersonalInfoDomain(
    val id: Long,
    val name: String,
    val middlename: String,
    val lastName: String,
    val avatarLink: String,
    val phoneNumber: String
)