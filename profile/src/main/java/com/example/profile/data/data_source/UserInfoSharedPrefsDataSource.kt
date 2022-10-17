package com.example.profile.data.data_source

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Single

interface UserInfoSharedPrefsDataSource {
    fun getCustomAvatarLink(): Single<String?>
    fun saveCustomAvatarLink(uri: Uri): Completable
}