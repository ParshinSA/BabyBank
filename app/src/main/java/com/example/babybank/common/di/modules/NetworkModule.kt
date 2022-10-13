package com.example.babybank.common.di.modules

import com.example.babybank.common.di.scope.AppScope
import com.example.babybank.data.networking.api.CurrencyRateApi
import com.example.babybank.data.networking.api.GoogleComApi
import com.example.firebase_api.api.Firebase
import com.example.firebase_api.api.FirebaseApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideGoogleComApi(retrofit: Retrofit): GoogleComApi {
        return retrofit
            .newBuilder()
            .baseUrl(URL_GOOGLE_COM)
            .build()
            .create()
    }


    @Provides
    @AppScope
    fun provideSecuritiesDataApi(retrofit: Retrofit): FirebaseApi {
        return Firebase.api(retrofit)
    }

    @Provides
    @AppScope
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
            .baseUrl(URL_CBR_RU)
            .build()
    }

    companion object {
        private const val URL_CBR_RU = "https://www.cbr-xml-daily.ru/"
        private const val URL_GOOGLE_COM = "https://google.com"
    }
}