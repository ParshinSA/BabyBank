package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import com.example.babybank.domain.repositories_intf.PersonalInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileFrgInteractorIml @Inject constructor(
    private val personalInfoRepository: PersonalInfoRepository,
    private val menuItemRepository: MenuItemRepository
) : ProfileFrgInteractor {

    override fun getPersonalInfo(): Single<PersonalInfoDomain> {
        return personalInfoRepository.getPersonalInfo()
    }

    override fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>> {
        return menuItemRepository.getMenu(request)
    }
}