package com.example.babybank.data.networking.api

import com.example.babybank.data.models.CurrencyRateDto
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyRateApi {

    @GET("daily_json.js")
    fun getCurrencyRate(): Single<CurrencyRateDto>
}