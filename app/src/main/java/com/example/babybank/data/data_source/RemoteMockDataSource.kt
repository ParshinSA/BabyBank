package com.example.babybank.data.data_source

import com.example.babybank.data.models.AccountInfoDto
import com.example.babybank.data.models.MenuItemDto
import com.example.babybank.data.models.PersonalInfoDto
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Single

interface RemoteMockDataSource {

    fun getAccountsInfo(): Single<List<AccountInfoDto>>
    fun getPersonalInfo(): Single<PersonalInfoDto>
    fun getMenu(request: RequestMenu): Single<List<MenuItemDto>>
}