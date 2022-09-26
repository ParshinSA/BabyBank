package com.example.babybank.data.data_source.remote

import com.example.babybank.data.data_source.interf.GoogleComDataSource
import com.example.babybank.data.networking.api.GoogleComApi
import okhttp3.ResponseBody
import javax.inject.Inject

class GoogleComDataSourceImpl @Inject constructor(
    private val googleComApi: GoogleComApi
) : GoogleComDataSource {

    override fun download(url: String): ResponseBody? {
        return googleComApi.downloadFile(url).execute().body()
    }

}