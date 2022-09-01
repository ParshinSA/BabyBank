package com.example.babybank.domain.repositories_intf

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Single

interface MenuItemRepository {
    fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>>
}