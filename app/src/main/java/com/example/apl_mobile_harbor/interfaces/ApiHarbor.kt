package com.example.apl_mobile_harbor.interfaces

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiHarbor {

    data class LoginRequest(val email: String, val senha: String)
    data class LoginResponse(
        val userId: Int,
        val nome: String,
        val email: String,
        val idEmpresa: Int,
        val token: String
    )

    @POST("/usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}