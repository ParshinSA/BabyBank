package com.example.profile.data.data_source

import com.example.firebase_api.api.FirebaseApi
import com.example.firebase_api.models.AccountInfoDto
import com.example.firebase_api.models.MenuItemDto
import com.example.firebase_api.models.PdfLinkDto
import com.example.firebase_api.models.PersonalInfoDto
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.Single
import javax.inject.Inject

class RemoteMockDataSourceImpl @Inject constructor(
    private val firebaseApi: FirebaseApi
) : RemoteMockDataSource {

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