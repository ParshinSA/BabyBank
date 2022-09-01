package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Single

interface DetailsTransitionFrgInteractor {
    fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>>
}