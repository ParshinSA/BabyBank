package com.example.babybank.common.di.modules

import com.example.babybank.common.cicerone_router.ActivityRouter
import com.example.babybank.common.cicerone_router.FragmentRouter
import com.example.babybank.common.di.scope.AppScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = create()

    @Provides
    @AppScope
    fun provideRouterAppActivity(): ActivityRouter {
        return ActivityRouter(cicerone.router)
    }

    @Provides
    @AppScope
    fun provideRouterContainerFrg(): FragmentRouter {
        return FragmentRouter(cicerone.router)
    }

    @Provides
    @AppScope
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}