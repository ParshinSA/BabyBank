package com.example.babybank.domain.repositories_intf

import android.net.Uri
import com.example.babybank.domain.models.PersonalInfoDomain
import io.reactivex.Completable
import io.reactivex.Single

interface PersonalInfoRepository {
    fun getPersonalInfo(): Single<PersonalInfoDomain>
    fun checkCustomAvatarLink(): Single<String?>
    fun saveCustomAvatarLink(uri: Uri): Completable
}