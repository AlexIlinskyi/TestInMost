package com.zaemloans.bistronakartu.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceGenerator {
    val BASE_URL = "https://backly.work"

    //public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    companion object{
        fun <S> createService(serviceClass: Class<S>?, token: String?): S {
            val builder = Retrofit.Builder().baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())

            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val original = chain.request()
                if (token != null) {
                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", token) //.header("Content-Type", "application/json")
                        //.header("Accept", "application/json")
                        .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                } else {
                    // Request customization: add request headers
                    val requestBuilder =
                        original.newBuilder() //.header("Content-Type", "application/json")
                            //.header("Accept", "application/json")
                            .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
            }.build()
            builder.client(client)
            val retrofit = builder.build()
            return retrofit.create(serviceClass)
        }
    }
}
