package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Single

interface WalletFrgInteractor {
    fun getMenu(requestMenu: RequestMenu): Single<List<MenuItemDomain>>
}