package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.models.CurrencyRateDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import com.example.babybank.domain.repositories_intf.AccountsInfoRepository
import com.example.babybank.domain.repositories_intf.CurrencyRateRepository
import com.example.babybank.domain.repositories_intf.PersonalInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class HomeFrgInteractorImpl @Inject constructor(
    private val personalInfoRepository: PersonalInfoRepository,
    private val accountsInfoRepository: AccountsInfoRepository,
    private val currencyRateRepository: CurrencyRateRepository,
) : HomeFrgInteractor {

    override fun getPersonalInformation(): Single<PersonalInfoDomain> {
        return personalInfoRepository.getPersonalInfo()
    }

    override fun getAccountsInfo(): Single<List<AccountInfoDomain>> {
        return accountsInfoRepository.getAccountsInfo()
    }

    override fun getCurrencyRate(request: RequestCurrencyRate): Single<CurrencyRateDomain> {
        return currencyRateRepository.getCurrencyRate(request)
    }
}