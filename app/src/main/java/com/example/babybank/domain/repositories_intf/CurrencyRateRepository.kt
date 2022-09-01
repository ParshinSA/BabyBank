package com.example.babybank.domain.repositories_intf

import com.example.babybank.domain.models.CurrencyRateDomain
import com.example.babybank.domain.models.RequestCurrencyRate
import io.reactivex.Single

interface CurrencyRateRepository {
    fun getCurrencyRate(request: RequestCurrencyRate): Single<CurrencyRateDomain>
}