package com.example.apl_mobile_harbor.retrofit

import com.example.apl_mobile_harbor.classes.auth.AuthInterceptor
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val URL_API = "https://localhost:8080"

    fun getApiHarbor(tokenManager: TokenManager): ApiHarbor {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        return Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiHarbor::class.java)
    }
}