package com.example.babybank.presentation

import android.app.Application
import android.os.StrictMode
import com.example.babybank.BuildConfig
import com.example.babybank.common.di.AppComponent
import com.example.babybank.common.di.DaggerAppComponent
import com.example.profile.common.di.settings.ProfileDependenciesProvider

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
        dependenciesProviders()
    }

    private fun dependenciesProviders() {
        ProfileDependenciesProvider.dependencies = appComponent.profileDependencies
    }

    private fun threadPolicy() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
        }
    }

}