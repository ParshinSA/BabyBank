package com.example.babybank.common.di.modules

import com.example.babybank.data.data_source.interf.CurrencyRateDataSource
import com.example.babybank.data.data_source.interf.GoogleComDataSource
import com.example.babybank.data.data_source.interf.MockDataSource
import com.example.babybank.data.data_source.remote.CurrencyRateDataSourceImpl
import com.example.babybank.data.data_source.remote.GoogleComDataSourceImpl
import com.example.babybank.data.data_source.remote.MockDataSourceImpl
import com.example.babybank.data.networking.api.CurrencyRateApi
import com.example.babybank.data.networking.api.GoogleComApi
import com.example.firebase_api.api.FirebaseApi
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun provideRemoteMockDataSourceImplToInterface(
        firebaseApi: FirebaseApi
    ): MockDataSource {
        return MockDataSourceImpl(firebaseApi)
    }

    @Provides
    fun provideRemoteCurrencyRateDataSourceImplToInterface(
        currencyRateApi: CurrencyRateApi
    ): CurrencyRateDataSource {
        return CurrencyRateDataSourceImpl(currencyRateApi = currencyRateApi)
    }

    @Provides
    fun provideRemoteGoogleComDataSourceImplToInterface(
        googleComApi: GoogleComApi
    ): GoogleComDataSource {
        return GoogleComDataSourceImpl(googleComApi = googleComApi)
    }
}