package com.example.babybank.common.di.modules

import com.example.babybank.common.di.scope.AppScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = create()

    @Provides
    @AppScope
    @Named(ACTIVITY_CONTAINER_ROUTER)
    fun provideRouterAppActivity(): Router {
        return cicerone.router
    }

    @Provides
    @AppScope
    @Named(FRAGMENT_ROUTER)
    fun provideRouterContainerFrg(): Router {
        return cicerone.router
    }

    @Provides
    @AppScope
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    companion object {
        const val ACTIVITY_CONTAINER_ROUTER = "ROUTER_APP_ACTIVITY"
        const val FRAGMENT_ROUTER = "ROUTER_CONTAINER_FRG"
    }
}