package com.example.babybank.common.di.modules

import com.example.babybank.common.constants.URL_CBR_RU
import com.example.babybank.common.constants.URL_FIREBASE_DB
import com.example.babybank.data.networking.api.CurrencyRateApi
import com.example.babybank.data.networking.api.FirebaseApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSecuritiesDataApi(retrofit: Retrofit): FirebaseApi {
        return retrofit
            .newBuilder()
            .baseUrl(URL_FIREBASE_DB)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideExchangeRateApi(retrofit: Retrofit): CurrencyRateApi {
        return retrofit
            .newBuilder()
            .baseUrl(URL_CBR_RU)
            .build()
            .create()
    }


    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_FIREBASE_DB)
            .build()
    }

}