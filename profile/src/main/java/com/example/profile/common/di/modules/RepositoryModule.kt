package com.example.profile.common.di.modules

import com.example.profile.data.data_source.RemoteMockDataSource
import com.example.profile.data.data_source.UserInfoSharedPrefsDataSource
import com.example.profile.data.repositories.MenuItemRepositoryImpl
import com.example.profile.data.repositories.PersonalInfoRepositoryImpl
import com.example.profile.domain.repositories.MenuItemRepository
import com.example.profile.domain.repositories.PersonalInfoRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providePersonalInfoRepositoryImplToInterface(
        remoteMockDataSource: RemoteMockDataSource,
        userInfoSharedPrefsDataSource: UserInfoSharedPrefsDataSource
    ): PersonalInfoRepository {
        return PersonalInfoRepositoryImpl(
            remoteMockDataSource = remoteMockDataSource,
            userInfoSharedPrefsDataSource = userInfoSharedPrefsDataSource
        )
    }

    @Provides
    fun provideMenuItemRepositoryImplToInterface(
        remoteMockDataSource: RemoteMockDataSource
    ): MenuItemRepository {
        return MenuItemRepositoryImpl(remoteMockDataSource = remoteMockDataSource)
    }
}