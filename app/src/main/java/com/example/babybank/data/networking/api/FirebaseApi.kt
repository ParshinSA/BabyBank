package com.example.babybank.data.networking.api

import com.example.babybank.data.models.AccountInfoDto
import com.example.babybank.data.models.MenuItemDto
import com.example.babybank.data.models.PdfLinkDto
import com.example.babybank.data.models.PersonalInfoDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FirebaseApi {

    @GET("menu/{nameMenu}.json")
    fun getMenu(
        @Path("nameMenu") nameMenu: String
    ): Single<List<MenuItemDto>>

    @GET("accounts.json")
    fun getAccountsInformation(): Single<List<AccountInfoDto>>

    @GET("profiles.json")
    fun getPersonalInformation(): Single<PersonalInfoDto>

    @GET("pdfLink.json")
    fun getPdfLink(): Single<PdfLinkDto>
}