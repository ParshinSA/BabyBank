package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babybank.domain.interactors.DetailsTransitionFrgInteractor
import com.example.babybank.domain.interactors.HomeFrgInteractor
import com.example.babybank.domain.interactors.ProfileFrgInteractor
import com.example.babybank.domain.interactors.WalletFrgInteractor
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class BaseFactoryViewModelFactory @Inject constructor(
    private val homeFrgInteractor: HomeFrgInteractor,
    private val profileFrgInteractor: ProfileFrgInteractor,
    private val walletFrgInteractor: WalletFrgInteractor,
    private val detailsTransitionFrgInteractor: DetailsTransitionFrgInteractor,
    private val router: Router,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(OnboardingFrgViewModel::class.java) ->
                OnboardingFrgViewModel(
                    router = router,
                ) as T

            modelClass.isAssignableFrom(AppActivityViewModel::class.java) ->
                AppActivityViewModel(
                    router = router
                ) as T

            modelClass.isAssignableFrom(ProfileFrgViewModel::class.java) ->
                ProfileFrgViewModel(
                    interactor = profileFrgInteractor,
                ) as T

            modelClass.isAssignableFrom(WalletFrgViewModel::class.java) ->
                WalletFrgViewModel(
                    interactor = walletFrgInteractor,
                ) as T

            modelClass.isAssignableFrom(HomeFrgViewModel::class.java) ->
                HomeFrgViewModel(
                    interactor = homeFrgInteractor,
                ) as T

            modelClass.isAssignableFrom(DetailsTransitionFrgViewModel::class.java) ->
                DetailsTransitionFrgViewModel(
                    interactor = detailsTransitionFrgInteractor
                ) as T

            else -> error("Incorrect view model factory $modelClass")
        }
    }
}