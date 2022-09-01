package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.RemoteCurrencyRateDataSource
import com.example.babybank.data.toCurrencyRateDomain
import com.example.babybank.domain.models.CurrencyRateDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import com.example.babybank.domain.repositories_intf.CurrencyRateRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRateRepositoryImpl @Inject constructor(
    private val dataSource: RemoteCurrencyRateDataSource
) : CurrencyRateRepository {

    override fun getCurrencyRate(request: RequestCurrencyRate): Single<CurrencyRateDomain> {
        return dataSource.getCurrencyRate()
            .map { currencyRateDto ->
                currencyRateDto.toCurrencyRateDomain(request)
            }
    }
}