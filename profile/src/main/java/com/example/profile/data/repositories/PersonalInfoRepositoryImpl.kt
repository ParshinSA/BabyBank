package com.example.profile.data.repositories

import android.net.Uri
import com.example.profile.data.data_source.RemoteMockDataSource
import com.example.profile.data.data_source.UserInfoSharedPrefsDataSource
import com.example.profile.data.toPersonalInfoDomain
import com.example.profile.domain.models.PersonalInfoDomain
import com.example.profile.domain.repositories.PersonalInfoRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PersonalInfoRepositoryImpl @Inject constructor(
    private val userInfoSharedPrefsDataSource: UserInfoSharedPrefsDataSource,
    private val remoteMockDataSource: RemoteMockDataSource,
) : PersonalInfoRepository {

    override fun getPersonalInfo(): Single<PersonalInfoDomain> {
        return remoteMockDataSource.getPersonalInfo()
            .map { it.toPersonalInfoDomain() }
    }

    override fun checkCustomAvatarLink(): Single<String?> {
        return userInfoSharedPrefsDataSource.getCustomAvatarLink()
    }

    override fun saveCustomAvatarLink(uri: Uri): Completable {
        return userInfoSharedPrefsDataSource.saveCustomAvatarLink(uri)
    }
}