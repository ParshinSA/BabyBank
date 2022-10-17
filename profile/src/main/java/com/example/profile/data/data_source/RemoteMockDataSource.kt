package com.example.profile.data.data_source

import com.example.firebase_api.models.AccountInfoDto
import com.example.firebase_api.models.MenuItemDto
import com.example.firebase_api.models.PdfLinkDto
import com.example.firebase_api.models.PersonalInfoDto
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.Single

interface RemoteMockDataSource {
    fun getAccountsInfo(): Single<List<AccountInfoDto>>
    fun getPersonalInfo(): Single<PersonalInfoDto>
    fun getMenu(request: RequestMenu): Single<List<MenuItemDto>>
    fun getPdfLink(): Single<PdfLinkDto>
}