package com.example.profile.common.di.modules

import com.example.profile.data.data_source.RemoteMockDataSource
import com.example.profile.data.data_source.RemoteMockDataSourceImpl
import com.example.profile.data.data_source.UserInfoSharedPrefsDataSource
import com.example.profile.data.data_source.UserInfoSharedPrefsDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
internal class DataSourceModule {

    @Provides
    fun provideRemoteMockDataSourceImplToInterface(remoteMockDataSourceImpl: RemoteMockDataSourceImpl): RemoteMockDataSource {
        return remoteMockDataSourceImpl
    }

    @Provides
    fun provideUserInfoSharedPrefsDataSourceToInterface(
        userInfoSharedPrefsDataSourceImpl: UserInfoSharedPrefsDataSourceImpl
    ): UserInfoSharedPrefsDataSource {
        return userInfoSharedPrefsDataSourceImpl
    }
}