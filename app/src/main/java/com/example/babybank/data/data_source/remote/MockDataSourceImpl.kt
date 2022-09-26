package com.example.babybank.data.data_source.remote

import com.example.babybank.data.data_source.interf.MockDataSource
import com.example.babybank.data.models.AccountInfoDto
import com.example.babybank.data.models.MenuItemDto
import com.example.babybank.data.models.PdfLinkDto
import com.example.babybank.data.models.PersonalInfoDto
import com.example.babybank.data.networking.api.FirebaseApi
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Single
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val firebaseApi: FirebaseApi
) : MockDataSource {

    override fun getMenu(request: RequestMenu): Single<List<MenuItemDto>> {
        return firebaseApi.getMenu(request.menuType.nameMenu)
    }

    override fun getPdfLink(): Single<PdfLinkDto> {
        return firebaseApi.getPdfLink()
    }

    override fun getAccountsInfo(): Single<List<AccountInfoDto>> {
        return firebaseApi.getAccountsInformation()
    }

    override fun getPersonalInfo(): Single<PersonalInfoDto> {
        return firebaseApi.getPersonalInformation()
    }

}