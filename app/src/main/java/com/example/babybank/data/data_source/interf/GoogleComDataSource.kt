package com.example.babybank.data.data_source.interf

import okhttp3.ResponseBody

interface GoogleComDataSource {
    fun download(url: String): ResponseBody?
}
