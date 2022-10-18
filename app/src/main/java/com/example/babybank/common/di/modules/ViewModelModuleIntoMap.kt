package com.example.babybank.common.di.modules

import androidx.lifecycle.ViewModel
import com.example.babybank.presentation.viewmodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ViewModelModuleIntoMap {

    @Binds
    @[IntoMap ClassKey(OnboardingFrgViewModel::class)]
    fun bindsOnboardingFrgViewModel(viewModel: OnboardingFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(ContainerActivityViewModel::class)]
    fun bindsContainerActivityViewModel(viewModel: ContainerActivityViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(WalletFrgViewModel::class)]
    fun bindsWalletFrgViewModel(viewModel: WalletFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(HomeFrgViewModel::class)]
    fun bindsHomeFrgViewModel(viewModel: HomeFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(DetailsTransitionFrgViewModel::class)]
    fun bindsDetailsTransitionFrgViewModel(viewModel: DetailsTransitionFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(ContainerFrgViewModel::class)]
    fun bindsContainerFrgViewModel(viewModel: ContainerFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(BtmSheetFragmentInWalletFrgViewModel::class)]
    fun bindsBtmSheetFragmentInWalletFrgViewModel(viewModel: BtmSheetFragmentInWalletFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(BankListFrgViewModel::class)]
    fun bindsBankListFrgViewModel(viewModel: BankListFrgViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(PdfViewerFrgViewModel::class)]
    fun bindsPdfViewerFrgViewModel(viewModel: PdfViewerFrgViewModel): ViewModel

}