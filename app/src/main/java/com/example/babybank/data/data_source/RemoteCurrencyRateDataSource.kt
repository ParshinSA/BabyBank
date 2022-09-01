package com.example.babybank.data.data_source

import com.example.babybank.data.models.CurrencyRateDto
import io.reactivex.Single

interface RemoteCurrencyRateDataSource {
    fun getCurrencyRate(): Single<CurrencyRateDto>
}