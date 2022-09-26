package com.example.babybank.data.data_source.interf

import com.example.babybank.data.models.CurrencyRateDto
import io.reactivex.Single

interface CurrencyRateDataSource {
    fun getCurrencyRate(): Single<CurrencyRateDto>
}