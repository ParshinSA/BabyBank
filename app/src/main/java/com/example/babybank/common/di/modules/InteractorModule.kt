package com.example.babybank.common.di.modules

import com.example.babybank.domain.interactors.*
import com.example.babybank.domain.repositories_intf.*
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideWalletFrgInteractorImplToInterface(
        menuItemRepository: MenuItemRepository
    ): WalletFrgInteractor {
        return WalletFrgInteractorImpl(menuItemRepository = menuItemRepository)
    }

    @Provides
    fun provideHomeFrgInteractorImplToInterface(
        personalInfoRepository: PersonalInfoRepository,
        accountsInfoRepository: AccountsInfoRepository,
        currencyRateRepository: CurrencyRateRepository
    ): HomeFrgInteractor {
        return HomeFrgInteractorImpl(
            personalInfoRepository = personalInfoRepository,
            accountsInfoRepository = accountsInfoRepository,
            currencyRateRepository = currencyRateRepository
        )
    }

    @Provides
    fun provideDetailsTransitionFrgInteractorImplToInterface(
        menuItemRepository: MenuItemRepository
    ): DetailsTransitionFrgInteractor {
        return DetailsTransitionFrgInteractorImpl(menuItemRepository = menuItemRepository)
    }

    @Provides
    fun provideBankListFrgInteractorImplToInterface(
        bankListRepository: BankListRepository
    ): BankListFrgInteractor {
        return BankListFrgInteractorImpl(bankListRepository = bankListRepository)
    }
}