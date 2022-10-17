package com.example.profile.common.di.settings

import android.content.Context
import com.example.firebase_api.api.FirebaseApi

interface ProfileDependencies {
    val firebaseApi: FirebaseApi
    val context: Context
}