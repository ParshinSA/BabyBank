package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.babybank.common.cicerone_router.ActivityRouter
import com.example.babybank.presentation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContainerActivityViewModel @Inject constructor(
    routerProvider: ActivityRouter
) : BaseViewModel() {
    private val router = routerProvider.router

    private val isExitAppMutLiveData = MutableLiveData(false)
    val isExitAppLiveData: LiveData<Boolean> get() = isExitAppMutLiveData

    fun startOnboarding() {
        router.navigateTo(Screens.OnboardingFrg())
    }

    fun firstClickExitApp() {
        isExitAppMutLiveData.postValue(true)
        cancelExitByTimer()
    }

    fun onBackPressed() {
        router.finishChain()
    }

    private fun cancelExitByTimer() {
        viewModelScope.launch {
            delay(CANCEL_EXIT_BY_TIMER)
            isExitAppMutLiveData.postValue(false)
        }
    }

    companion object {
        private const val CANCEL_EXIT_BY_TIMER = 2000L
    }
}