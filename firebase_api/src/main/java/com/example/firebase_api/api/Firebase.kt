package com.example.firebase_api.api

import retrofit2.Retrofit
import retrofit2.create

object Firebase {

    fun api(retrofit: Retrofit): FirebaseApi {
        return retrofit.newBuilder()
            .baseUrl(URL_FIREBASE_DB)
            .build()
            .create()
    }

    private const val URL_FIREBASE_DB =
        "https://babybank-14bb0-default-rtdb.europe-west1.firebasedatabase.app/"
}
