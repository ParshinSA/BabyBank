package com.example.babybank.domain.repositories_intf

import com.example.babybank.domain.models.PersonalInfoDomain
import io.reactivex.Single

interface PersonalInfoRepository {
    fun getPersonalInfo(): Single<PersonalInfoDomain>
}