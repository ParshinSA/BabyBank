package com.example.babybank.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.babybank.common.di.modules.NavigationModule.Companion.ACTIVITY_CONTAINER_ROUTER
import com.example.babybank.common.di.modules.NavigationModule.Companion.FRAGMENT_ROUTER
import com.example.babybank.data.common.utils.AppExternalStorage
import com.example.babybank.domain.interactors.*
import com.example.babybank.presentation.common.FileUriProvider
import com.example.babybank.presentation.common.MoneyFormatter
import com.example.babybank.presentation.models.ConvertersDomainToUi
import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class ViewModelFactory @Inject constructor(
    private val detailsTransitionFrgInteractor: DetailsTransitionFrgInteractor,
    private val appExternalStorage: AppExternalStorage,
    private val bankListFrgInteractor: BankListFrgInteractor,
    private val profileFrgInteractor: ProfileFrgInteractor,
    private val walletFrgInteractor: WalletFrgInteractor,
    private val homeFrgInteractor: HomeFrgInteractor,
    private val converters: ConvertersDomainToUi,
    private val fileUriProvider: FileUriProvider,
    private val moneyFormatter: MoneyFormatter,
    @Named(ACTIVITY_CONTAINER_ROUTER)
    private val activityRouter: Router,
    @Named(FRAGMENT_ROUTER)
    private val fragmentRouter: Router,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(OnboardingFrgViewModel::class.java) ->
                OnboardingFrgViewModel(
                    router = activityRouter,
                ) as T

            modelClass.isAssignableFrom(ContainerActivityViewModel::class.java) ->
                ContainerActivityViewModel(
                    router = activityRouter
                ) as T

            modelClass.isAssignableFrom(ProfileFrgViewModel::class.java) ->
                ProfileFrgViewModel(
                    interactor = profileFrgInteractor,
                    converters = converters
                ) as T

            modelClass.isAssignableFrom(WalletFrgViewModel::class.java) ->
                WalletFrgViewModel(
                    interactor = walletFrgInteractor,
                    parentRouter = fragmentRouter,
                    converters = converters,
                ) as T

            modelClass.isAssignableFrom(HomeFrgViewModel::class.java) ->
                HomeFrgViewModel(
                    moneyFormatter = moneyFormatter,
                    interactor = homeFrgInteractor,
                    parentRouter = fragmentRouter,
                    converters = converters,
                ) as T

            modelClass.isAssignableFrom(DetailsTransitionFrgViewModel::class.java) ->
                DetailsTransitionFrgViewModel(
                    interactor = detailsTransitionFrgInteractor,
                    parentRouter = fragmentRouter,
                    converters = converters
                ) as T

            modelClass.isAssignableFrom(ContainerFrgViewModel::class.java) ->
                ContainerFrgViewModel() as T

            modelClass.isAssignableFrom(BtmSheetFragmentInWalletFrgViewModel::class.java) ->
                BtmSheetFragmentInWalletFrgViewModel(
                    parentRouter = fragmentRouter
                ) as T

            modelClass.isAssignableFrom(BankListFrgViewModel::class.java) ->
                BankListFrgViewModel(
                    appExternalStorage = appExternalStorage,
                    interactor = bankListFrgInteractor,
                    fileUriProvider = fileUriProvider,
                    parentRouter = fragmentRouter,
                ) as T

            modelClass.isAssignableFrom(PdfViewerFrgViewModel::class.java) ->
                PdfViewerFrgViewModel(
                    appExternalStorage = appExternalStorage,
                    parentRouter = fragmentRouter,
                ) as T

            else -> error("Incorrect view model factory $modelClass")
        }
    }
}