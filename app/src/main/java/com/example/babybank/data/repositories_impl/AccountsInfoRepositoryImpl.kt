package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.RemoteMockDataSource
import com.example.babybank.data.toAccountInfoDomain
import com.example.babybank.domain.models.AccountInfoDomain
import com.example.babybank.domain.repositories_intf.AccountsInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class AccountsInfoRepositoryImpl @Inject constructor(
    private val dataSource: RemoteMockDataSource
) : AccountsInfoRepository {

    override fun getAccountsInfo(): Single<List<AccountInfoDomain>> {
        return dataSource.getAccountsInfo()
            .map { accountInfoList ->
                accountInfoList.map { it.toAccountInfoDomain() }
            }
    }
}