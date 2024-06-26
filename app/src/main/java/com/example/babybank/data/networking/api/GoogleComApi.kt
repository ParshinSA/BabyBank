package com.example.babybank.data.networking.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleComApi {

    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>
}