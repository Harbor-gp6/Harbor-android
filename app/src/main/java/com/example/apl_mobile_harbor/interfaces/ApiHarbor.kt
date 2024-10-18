package com.example.apl_mobile_harbor.interfaces

import com.example.apl_mobile_harbor.classes.pedido.Pedido
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/pedidos/pedidosAbertos")
    suspend fun getPedidos(): Response<List<Pedido>>
}