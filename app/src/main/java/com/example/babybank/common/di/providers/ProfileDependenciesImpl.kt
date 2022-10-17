package com.example.babybank.common.di.providers

import android.content.Context
import com.example.babybank.common.di.scope.AppScope
import com.example.firebase_api.api.FirebaseApi
import com.example.profile.common.di.settings.ProfileDependencies
import javax.inject.Inject

@AppScope
class ProfileDependenciesImpl @Inject constructor(
    override val firebaseApi: FirebaseApi,
    override val context: Context
) : ProfileDependencies