package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babybank.common.constants.CONTAINER_ACTIVITY_ROUTER
import com.example.babybank.common.constants.CONTAINER_FRAGMENT_ROUTER
import com.example.babybank.domain.interactors.DetailsTransitionFrgInteractor
import com.example.babybank.domain.interactors.HomeFrgInteractor
import com.example.babybank.domain.interactors.ProfileFrgInteractor
import com.example.babybank.domain.interactors.WalletFrgInteractor
import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class BaseFactoryViewModelFactory @Inject constructor(
    private val homeFrgInteractor: HomeFrgInteractor,
    private val profileFrgInteractor: ProfileFrgInteractor,
    private val walletFrgInteractor: WalletFrgInteractor,
    private val detailsTransitionFrgInteractor: DetailsTransitionFrgInteractor,
    @Named(CONTAINER_ACTIVITY_ROUTER)
    private val containerActivityRouter: Router,
    @Named(CONTAINER_FRAGMENT_ROUTER)
    private val containerFragmentRouter: Router,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(OnboardingFrgViewModel::class.java) ->
                OnboardingFrgViewModel(
                    router = containerActivityRouter,
                ) as T

            modelClass.isAssignableFrom(ContainerActivityViewModel::class.java) ->
                ContainerActivityViewModel(
                    router = containerActivityRouter
                ) as T

            modelClass.isAssignableFrom(ProfileFrgViewModel::class.java) ->
                ProfileFrgViewModel(
                    interactor = profileFrgInteractor,
                ) as T

            modelClass.isAssignableFrom(WalletFrgViewModel::class.java) ->
                WalletFrgViewModel(
                    interactor = walletFrgInteractor,
                    parentRouter = containerFragmentRouter
                ) as T

            modelClass.isAssignableFrom(HomeFrgViewModel::class.java) ->
                HomeFrgViewModel(
                    interactor = homeFrgInteractor,
                    parentRouter = containerFragmentRouter
                ) as T

            modelClass.isAssignableFrom(DetailsTransitionFrgViewModel::class.java) ->
                DetailsTransitionFrgViewModel(
                    interactor = detailsTransitionFrgInteractor,
                    parentRouter = containerFragmentRouter
                ) as T

            modelClass.isAssignableFrom(ContainerFrgViewModel::class.java) ->
                ContainerFrgViewModel() as T

            modelClass.isAssignableFrom(BtmSheetFragmentInWalletFrgViewModel::class.java) ->
                BtmSheetFragmentInWalletFrgViewModel(
                    parentRouter = containerFragmentRouter
                ) as T

            else -> error("Incorrect view model factory $modelClass")
        }
    }
}