package com.example.babybank.presentation.viewmodels

import com.example.babybank.common.cicerone_router.ActivityRouter
import com.example.babybank.presentation.Screens
import javax.inject.Inject

class OnboardingFrgViewModel @Inject constructor(
//    @Named(ACTIVITY_CONTAINER_ROUTER)
    private val routerProvider: ActivityRouter,
) : BaseViewModel() {
    private val router = routerProvider.router


    fun fabNextClick() {
        router.replaceScreen(Screens.ContainerFrg())
    }
}