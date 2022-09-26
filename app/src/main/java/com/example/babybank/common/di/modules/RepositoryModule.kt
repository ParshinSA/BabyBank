package com.example.babybank.common.di.modules

import com.example.babybank.data.data_source.interf.CurrencyRateDataSource
import com.example.babybank.data.data_source.interf.MockDataSource
import com.example.babybank.data.data_source.remote.DownloadManagerDownloadDataSourceImpl
import com.example.babybank.data.data_source.remote.GetResponseDownloadDataSourceImpl
import com.example.babybank.data.repositories_impl.*
import com.example.babybank.domain.repositories_intf.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideAccountsInfoRepositoryImplToInterface(
        mockDataSource: MockDataSource
    ): AccountsInfoRepository {
        return AccountsInfoRepositoryImpl(mockDataSource = mockDataSource)
    }

    @Provides
    fun provideCurrencyRateRepositoryImplToInterface(
        currencyRateDataSource: CurrencyRateDataSource
    ): CurrencyRateRepository {
        return CurrencyRateRepositoryImpl(currencyRateDataSource = currencyRateDataSource)
    }

    @Provides
    fun provideMenuItemRepositoryImplToInterface(
        mockDataSource: MockDataSource
    ): MenuItemRepository {
        return MenuItemRepositoryImpl(mockDataSource = mockDataSource)
    }

    @Provides
    fun providePersonalInfoRepositoryImplToInterface(
        mockDataSource: MockDataSource
    ): PersonalInfoRepository {
        return PersonalInfoRepositoryImpl(mockDataSource = mockDataSource)
    }

    @Provides
    fun provideBankListRepositoryImplToInterface(
        downloadManagerDownloadDataSourceImpl: DownloadManagerDownloadDataSourceImpl,
        getResponseDownloadDataSourceImpl: GetResponseDownloadDataSourceImpl,
        mockDataSource: MockDataSource,
    ): BankListRepository {
        return BankListRepositoryImpl(
            downloadManagerDownloadDataSourceImpl = downloadManagerDownloadDataSourceImpl,
            getResponseDownloadDataSourceImpl = getResponseDownloadDataSourceImpl,
            mockDataSource = mockDataSource,
        )
    }
}