package com.example.profile.common.di

import com.example.profile.common.di.modules.DataSourceModule
import com.example.profile.common.di.modules.InteractorModule
import com.example.profile.common.di.modules.RepositoryModule
import com.example.profile.common.di.settings.ProfileDependencies
import com.example.profile.common.di.settings.ProfileScope
import com.example.profile.presentation.ProfileFragment
import dagger.Component

@ProfileScope
@Component(
    dependencies = [ProfileDependencies::class],
    modules = [
        RepositoryModule::class,
        InteractorModule::class,
        DataSourceModule::class,
    ]
)
internal interface ProfileComponent {

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: ProfileDependencies): ProfileComponent
    }
}