package com.example.babybank.presentation.viewmodels

import com.example.babybank.presentation.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class OnboardingFrgViewModel @Inject constructor(
    private val router: Router,
) : BaseViewModel() {

    fun fabNextClick() {
        router.replaceScreen(Screens.ContainerFrg())
    }
}