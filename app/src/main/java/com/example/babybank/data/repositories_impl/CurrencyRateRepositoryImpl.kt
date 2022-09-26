package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.interf.CurrencyRateDataSource
import com.example.babybank.data.toCurrencyRateDomain
import com.example.babybank.domain.models.CurrencyRateDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import com.example.babybank.domain.repositories_intf.CurrencyRateRepository
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRateRepositoryImpl @Inject constructor(
    private val currencyRateDataSource: CurrencyRateDataSource
) : CurrencyRateRepository {

    override fun getCurrencyRate(request: RequestCurrencyRate): Single<CurrencyRateDomain> {
        return currencyRateDataSource.getCurrencyRate()
            .map { currencyRateDto ->
                currencyRateDto.toCurrencyRateDomain(request)
            }
    }
}