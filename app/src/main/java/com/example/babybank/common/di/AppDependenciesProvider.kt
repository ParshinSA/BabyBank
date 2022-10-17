package com.example.babybank.common.di

import com.example.profile.common.di.settings.ProfileDependencies

interface AppDependenciesProvider {
    val profileDependencies: ProfileDependencies
}
