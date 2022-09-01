package com.example.babybank.domain.repositories_intf

import com.example.babybank.domain.models.AccountInfoDomain
import io.reactivex.Single

interface AccountsInfoRepository {
    fun getAccountsInfo(): Single<List<AccountInfoDomain>>
}