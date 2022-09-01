package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import io.reactivex.Single
import javax.inject.Inject

class DetailsTransitionFrgInteractorImpl @Inject constructor(
    private val menuItemRepository: MenuItemRepository
) : DetailsTransitionFrgInteractor {

    override fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>> {
        return menuItemRepository.getMenu(request)
    }
}