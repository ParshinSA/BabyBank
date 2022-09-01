package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.models.CurrencyRateDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import io.reactivex.Single

interface HomeFrgInteractor {
    fun getPersonalInformation(): Single<PersonalInfoDomain>
    fun getAccountsInfo(): Single<List<AccountInfoDomain>>
    fun getCurrencyRate(request: RequestCurrencyRate): Single<CurrencyRateDomain>
}