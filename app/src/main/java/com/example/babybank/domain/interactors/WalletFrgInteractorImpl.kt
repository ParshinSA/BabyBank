package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import io.reactivex.Single
import javax.inject.Inject

class WalletFrgInteractorImpl @Inject constructor(
    private val menuItemRepository: MenuItemRepository
) : WalletFrgInteractor {

    override fun getMenu(requestMenu: RequestMenu): Single<List<MenuItemDomain>> {
        return menuItemRepository.getMenu(requestMenu)
    }
}