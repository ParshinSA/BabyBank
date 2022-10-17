package com.example.profile.data

import com.example.firebase_api.models.MenuItemDto
import com.example.firebase_api.models.PersonalInfoDto
import com.example.profile.domain.models.MenuItemDomain
import com.example.profile.domain.models.PersonalInfoDomain

fun MenuItemDto.toMenuItemDomain(): MenuItemDomain {
    return MenuItemDomain(
        title = title ?: "",
        idIcon = idIcon ?: ""
    )
}

fun PersonalInfoDto.toPersonalInfoDomain(): PersonalInfoDomain {
    return PersonalInfoDomain(
        id = id ?: 0,
        name = name ?: "",
        middleName = middleName ?: "",
        lastName = lastName ?: "",
        phoneNumber = phoneNumber ?: ""
    )
}
