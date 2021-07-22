package com.zaemloans.bistronakartu.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface InfoService {
    @GET
    fun getBy(@Url url: String): Call<ResponseBody>
}
