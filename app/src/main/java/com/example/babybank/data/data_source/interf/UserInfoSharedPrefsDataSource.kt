package com.example.babybank.data.data_source.interf

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Single

interface UserInfoSharedPrefsDataSource {
    fun getCustomAvatarLink(): Single<String?>
    fun saveCustomAvatarLink(uri: Uri): Completable
}