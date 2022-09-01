package com.example.babybank.data.data_source

import com.example.babybank.data.models.CurrencyRateDto
import com.example.babybank.data.networking.api.CurrencyRateApi
import io.reactivex.Single
import javax.inject.Inject

class RemoteCurrencyRateDataSourceImpl @Inject constructor(
    private val currencyRateApi: CurrencyRateApi
) : RemoteCurrencyRateDataSource {

    override fun getCurrencyRate(): Single<CurrencyRateDto> {
        return currencyRateApi.getCurrencyRate()
    }
}