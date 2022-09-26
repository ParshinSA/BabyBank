package com.example.babybank.data.data_source.remote

import com.example.babybank.data.data_source.interf.CurrencyRateDataSource
import com.example.babybank.data.models.CurrencyRateDto
import com.example.babybank.data.networking.api.CurrencyRateApi
import io.reactivex.Single
import javax.inject.Inject

class CurrencyRateDataSourceImpl @Inject constructor(
    private val currencyRateApi: CurrencyRateApi
) : CurrencyRateDataSource {

    override fun getCurrencyRate(): Single<CurrencyRateDto> {
        return currencyRateApi.getCurrencyRate()
    }
}