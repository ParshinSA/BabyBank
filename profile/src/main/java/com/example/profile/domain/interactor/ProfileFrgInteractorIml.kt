package com.example.profile.domain.interactor

import android.net.Uri
import com.example.profile.domain.models.MenuItemDomain
import com.example.profile.domain.models.PersonalInfoDomain
import com.example.profile.domain.repositories.MenuItemRepository
import com.example.profile.domain.repositories.PersonalInfoRepository
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.Completable
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

    override fun checkCustomAvatarLink(): Single<String?> {
        return personalInfoRepository.checkCustomAvatarLink()
    }

    override fun saveCustomAvatarLink(uri: Uri): Completable {
        return personalInfoRepository.saveCustomAvatarLink(uri)
    }
}