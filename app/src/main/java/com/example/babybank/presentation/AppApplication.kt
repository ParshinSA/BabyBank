package com.example.babybank.presentation

import android.app.Application
import com.example.babybank.common.di.AppComponent
import com.example.babybank.common.di.DaggerAppComponent

class AppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .context(context = this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        threadPolicy()
    }

    private fun threadPolicy() {
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(
//                StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork()
//                    .penaltyDeath()
//                    .build()
//            )
//        }
    }

}