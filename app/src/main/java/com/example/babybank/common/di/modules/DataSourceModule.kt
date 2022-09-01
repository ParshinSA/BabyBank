package com.example.babybank.common.di.modules

import com.example.babybank.data.data_source.RemoteCurrencyRateDataSource
import com.example.babybank.data.data_source.RemoteCurrencyRateDataSourceImpl
import com.example.babybank.data.data_source.RemoteMockDataSource
import com.example.babybank.data.data_source.RemoteMockDataSourceImpl
import com.example.babybank.data.networking.api.CurrencyRateApi
import com.example.babybank.data.networking.api.FirebaseApi
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun provideRemoteMockDataSourceImplToInterface(
        firebaseApi: FirebaseApi
    ): RemoteMockDataSource {
        return RemoteMockDataSourceImpl(firebaseApi)
    }

    @Provides
    fun provideRemoteCurrencyRateDataSourceImplToInterface(
        currencyRateApi: CurrencyRateApi
    ): RemoteCurrencyRateDataSource {
        return RemoteCurrencyRateDataSourceImpl(currencyRateApi = currencyRateApi)
    }

}