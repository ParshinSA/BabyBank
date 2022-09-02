package com.example.babybank.common.di.modules

import com.example.babybank.common.constants.CONTAINER_ACTIVITY_ROUTER
import com.example.babybank.common.constants.CONTAINER_FRAGMENT_ROUTER
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = create()

    @Provides
    @Singleton
    @Named(CONTAINER_ACTIVITY_ROUTER)
    fun provideRouterAppActivity(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    @Named(CONTAINER_FRAGMENT_ROUTER)
    fun provideRouterContainerFrg(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}