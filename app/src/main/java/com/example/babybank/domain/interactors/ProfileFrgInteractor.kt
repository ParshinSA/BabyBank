package com.example.babybank.domain.interactors

import android.net.Uri
import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.domain.models.RequestMenu
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileFrgInteractor {
    fun getPersonalInfo(): Single<PersonalInfoDomain>
    fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>>
    fun checkCustomAvatarLink(): Single<String?>
    fun saveCustomAvatarLink(uri: Uri): Completable
}