package com.example.babybank.common.di.modules

import com.example.babybank.common.di.providers.ProfileDependenciesImpl
import com.example.profile.common.di.settings.ProfileDependencies
import dagger.Module
import dagger.Provides

@Module
class DependenciesProviderModule {

    @Provides
    fun provideProfileDependenciesImplToInterface(
        profileDependenciesImpl: ProfileDependenciesImpl
    ): ProfileDependencies {
        return profileDependenciesImpl
    }
}