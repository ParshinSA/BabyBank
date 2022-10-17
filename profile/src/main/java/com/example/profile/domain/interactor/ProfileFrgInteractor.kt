package com.example.profile.domain.interactor

import android.net.Uri
import com.example.profile.domain.models.MenuItemDomain
import com.example.profile.domain.models.PersonalInfoDomain
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileFrgInteractor {
    fun getPersonalInfo(): Single<PersonalInfoDomain>
    fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>>
    fun checkCustomAvatarLink(): Single<String?>
    fun saveCustomAvatarLink(uri: Uri): Completable
}