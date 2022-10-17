package com.example.profile.common.di.modules

import com.example.profile.domain.interactor.ProfileFrgInteractor
import com.example.profile.domain.interactor.ProfileFrgInteractorIml
import com.example.profile.domain.repositories.MenuItemRepository
import com.example.profile.domain.repositories.PersonalInfoRepository
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

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

}