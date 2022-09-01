package com.example.babybank.domain.interactors

import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Single

interface ProfileFrgInteractor {
    fun getPersonalInfo(): Single<PersonalInfoDomain>
    fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>>
}