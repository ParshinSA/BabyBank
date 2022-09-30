package com.example.babybank.data.data_source.interf

import io.reactivex.Completable

interface DownloadDataSource {

    fun download(url: String, directory: String): Completable
}