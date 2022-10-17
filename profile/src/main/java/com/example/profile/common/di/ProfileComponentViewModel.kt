package com.example.profile.common.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.profile.common.di.settings.ProfileDependenciesProvider

class ProfileComponentViewModel(application: Application) : AndroidViewModel(application) {

    internal val component = DaggerProfileComponent.builder()
        .dependencies(ProfileDependenciesProvider.dependencies)
        .build()

}