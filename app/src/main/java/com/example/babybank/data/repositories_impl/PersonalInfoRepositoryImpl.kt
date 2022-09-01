package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.RemoteMockDataSource
import com.example.babybank.data.toPersonalInfoDomain
import com.example.babybank.domain.models.PersonalInfoDomain
import com.example.babybank.domain.repositories_intf.PersonalInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class PersonalInfoRepositoryImpl @Inject constructor(
    private val dataSource: RemoteMockDataSource
) : PersonalInfoRepository {

    override fun getPersonalInfo(): Single<PersonalInfoDomain> {
        return dataSource.getPersonalInfo()
            .map { it.toPersonalInfoDomain() }
    }
}