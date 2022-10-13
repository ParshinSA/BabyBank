package com.example.babybank.data.data_source.interf

import com.example.babybank.domain.models.RequestMenu
import com.example.firebase_api.models.AccountInfoDto
import com.example.firebase_api.models.MenuItemDto
import com.example.firebase_api.models.PdfLinkDto
import com.example.firebase_api.models.PersonalInfoDto
import io.reactivex.Single

interface MockDataSource {
    fun getAccountsInfo(): Single<List<AccountInfoDto>>
    fun getPersonalInfo(): Single<PersonalInfoDto>
    fun getMenu(request: RequestMenu): Single<List<MenuItemDto>>
    fun getPdfLink(): Single<PdfLinkDto>
}