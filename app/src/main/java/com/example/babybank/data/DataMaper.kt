package com.example.babybank.data

import com.example.babybank.data.models.AccountInfoDto
import com.example.babybank.data.models.CurrencyRateDto
import com.example.babybank.data.models.MenuItemDto
import com.example.babybank.data.models.PersonalInfoDto
import com.example.babybank.domain.models.*

fun MenuItemDto.toMenuItemDomain(): MenuItemDomain {
    return MenuItemDomain(
        title = title ?: "",
        idIcon = idIcon ?: ""
    )
}

fun AccountInfoDto.toAccountInfoDomain(): AccountInfoDomain {
    val result = AccountInfoDomain(
        id = id ?: 0,
        balance = balance ?: 0.0,
        currencyType = currencyType ?: "",
        cards = cards?.map { cardDto ->
            CardDomain(id = cardDto.id ?: 0)
        } ?: emptyList()
    )
    return result
}

fun PersonalInfoDto.toPersonalInfoDomain(): PersonalInfoDomain {
    return PersonalInfoDomain(
        id = id ?: 0,
        name = name ?: "",
        middlename = middlename ?: "",
        lastName = lastName ?: "",
        avatarLink = avatarLink ?: "",
        phoneNumber = phoneNumber ?: ""
    )
}

fun CurrencyRateDto.toCurrencyRateDomain(requestCurrencyRate: RequestCurrencyRate): CurrencyRateDomain {
    return CurrencyRateDomain(
        value = when (requestCurrencyRate.currencyType) {
            CurrencyTypeDomain.USD -> valuteData.usdValute.value
            CurrencyTypeDomain.EUR -> valuteData.eurValute.value
            CurrencyTypeDomain.RUB -> 1.0
        }
    )
}