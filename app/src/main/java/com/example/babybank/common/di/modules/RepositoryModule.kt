package com.example.babybank.common.di.modules

import com.example.babybank.data.data_source.RemoteCurrencyRateDataSource
import com.example.babybank.data.data_source.RemoteMockDataSource
import com.example.babybank.data.repositories_impl.AccountsInfoRepositoryImpl
import com.example.babybank.data.repositories_impl.CurrencyRateRepositoryImpl
import com.example.babybank.data.repositories_impl.MenuItemRepositoryImpl
import com.example.babybank.data.repositories_impl.PersonalInfoRepositoryImpl
import com.example.babybank.domain.repositories_intf.AccountsInfoRepository
import com.example.babybank.domain.repositories_intf.CurrencyRateRepository
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import com.example.babybank.domain.repositories_intf.PersonalInfoRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideAccountsInfoRepositoryImplToInterface(
        dataSource: RemoteMockDataSource
    ): AccountsInfoRepository {
        return AccountsInfoRepositoryImpl(dataSource)
    }

    @Provides
    fun provideCurrencyRateRepositoryImplToInterface(
        dataSource: RemoteCurrencyRateDataSource
    ): CurrencyRateRepository {
        return CurrencyRateRepositoryImpl(dataSource)
    }

    @Provides
    fun provideMenuItemRepositoryImplToInterface(
        dataSource: RemoteMockDataSource
    ): MenuItemRepository {
        return MenuItemRepositoryImpl(dataSource)
    }

    @Provides
    fun providePersonalInfoRepositoryImplToInterface(
        dataSource: RemoteMockDataSource
    ): PersonalInfoRepository {
        return PersonalInfoRepositoryImpl(dataSource)
    }
}