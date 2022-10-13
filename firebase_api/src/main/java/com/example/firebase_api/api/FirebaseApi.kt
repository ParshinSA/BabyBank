package com.example.firebase_api.api

import com.example.firebase_api.models.AccountInfoDto
import com.example.firebase_api.models.MenuItemDto
import com.example.firebase_api.models.PdfLinkDto
import com.example.firebase_api.models.PersonalInfoDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FirebaseApi {

    @GET("menu/{nameMenu}.json")
    fun getMenu(@Path("nameMenu") nameMenu: String): Single<List<MenuItemDto>>

    @GET("accounts.json")
    fun getAccountsInformation(): Single<List<AccountInfoDto>>

    @GET("profiles.json")
    fun getPersonalInformation(): Single<PersonalInfoDto>

    @GET("pdfLink.json")
    fun getPdfLink(): Single<PdfLinkDto>

}
