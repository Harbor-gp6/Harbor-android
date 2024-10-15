package com.example.apl_mobile_harbor.classes.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()

        val newRequest = originalRequest.newBuilder().apply {
            if (!token.isNullOrEmpty()) {
                header("Authorization", "Bearer $token")
            }
        }.build()

        return chain.proceed(newRequest)
    }
}

