package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.babybank.common.constants.CANCEL_EXIT_BY_TIMER
import com.example.babybank.common.constants.CONTAINER_ACTIVITY_ROUTER
import com.example.babybank.presentation.Screens
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class ContainerActivityViewModel @Inject constructor(
    @Named(CONTAINER_ACTIVITY_ROUTER)
    private val router: Router
) : BaseViewModel() {

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
}