package com.example.babybank.common.di.modules

import com.example.babybank.domain.interactors.*
import com.example.babybank.domain.repositories_intf.AccountsInfoRepository
import com.example.babybank.domain.repositories_intf.CurrencyRateRepository
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import com.example.babybank.domain.repositories_intf.PersonalInfoRepository
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
    fun provideProfileFrgInteractorImplToInterface(
        personalInfoRepository: PersonalInfoRepository,
        menuItemRepository: MenuItemRepository
    ): ProfileFrgInteractor {
        return ProfileFrgInteractorIml(
            personalInfoRepository = personalInfoRepository,
            menuItemRepository = menuItemRepository
        )
    }

    @Provides
    fun provideDetailsTransitionFrgInteractorImplToInterface(
        menuItemRepository: MenuItemRepository
    ): DetailsTransitionFrgInteractor {
        return DetailsTransitionFrgInteractorImpl(menuItemRepository = menuItemRepository)
    }
}