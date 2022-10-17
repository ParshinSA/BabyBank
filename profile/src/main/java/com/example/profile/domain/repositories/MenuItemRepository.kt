package com.example.profile.domain.repositories

import com.example.profile.domain.models.MenuItemDomain
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.Single

interface MenuItemRepository {
    fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>>
}