package com.example.babybank.common.di.modules

import com.example.babybank.data.networking.api.CurrencyRateApi
import com.example.babybank.data.networking.api.FirebaseApi
import com.example.babybank.data.networking.api.GoogleComApi
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
    fun provideGoogleComApi(retrofit: Retrofit): GoogleComApi {
        return retrofit
            .newBuilder()
            .baseUrl(URL_GOOGLE_COM)
            .build()
            .create()
    }

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

    companion object {
        private const val URL_FIREBASE_DB =
            "https://babybank-14bb0-default-rtdb.europe-west1.firebasedatabase.app/"

        private const val URL_CBR_RU = "https://www.cbr-xml-daily.ru/"
        private const val URL_GOOGLE_COM = "https://google.com"
    }
}