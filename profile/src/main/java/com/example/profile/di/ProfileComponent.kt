package com.example.profile.di

import dagger.Component

@ProfileScope
@Component(dependencies = [ProfileDependencies::class])
interface ProfileComponent {
}