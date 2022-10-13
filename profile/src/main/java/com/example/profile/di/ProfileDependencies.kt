package com.example.profile.di

import com.example.firebase_api.api.FirebaseApi

interface ProfileDependencies {
    val firebaseApi: FirebaseApi
}