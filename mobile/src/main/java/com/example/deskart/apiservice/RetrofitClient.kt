package com.example.deskart.apiservice
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5159/api/"

    val instance: AuthApiService by lazy {
        val retrofit = Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build()
        retrofit.create(AuthApiService::class.java)
    }
}