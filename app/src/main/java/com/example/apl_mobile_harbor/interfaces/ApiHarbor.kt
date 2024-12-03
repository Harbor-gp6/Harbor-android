package com.example.apl_mobile_harbor.interfaces

import com.example.apl_mobile_harbor.classes.atividade.Atividade
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.classes.avaliacao.Avaliacao
import com.example.apl_mobile_harbor.classes.empresa.Empresa
import com.example.apl_mobile_harbor.classes.pedido.Pedido
import com.example.apl_mobile_harbor.classes.pedido.PedidoCriacao
import com.example.apl_mobile_harbor.classes.prestador.Prestador
import com.example.apl_mobile_harbor.classes.produto.Produto
import com.example.apl_mobile_harbor.classes.servico.Servico
import com.example.apl_mobile_harbor.classes.usuario.UsuarioAtualizacao
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/pedidos/pedidosPorData")
    suspend fun getPedidosPorData(@Query("data") data: String): Response<List<Pedido>>

    @GET("/pedidos/{codigoPedido}")
    suspend fun getPedidoPorCodigo(@Path("codigoPedido") codigoPedido: String): Response<Pedido>

    @GET("/avaliacoes/media-prestador")
    suspend fun getMedia(): Response<Double>

    @GET("/servicos")
    suspend fun getServicos(): Response<List<Servico>>

    @GET("/produtos")
    suspend fun getProdutos(): Response<List<Produto>>

    @GET("/usuarios/empresa/{empresaId}")
    suspend fun getPrestadores(@Path("empresaId") empresaId: Int): Response<List<Prestador>>

    @GET("/usuarios/{id}")
    suspend fun getPrestadorPorId(@Path("id") id: Int): Response<Prestador>

    @GET("/empresas/{id}")
    suspend fun getEmpresaPorId(@Path("id") id: Int): Response<Empresa>

    @GET("/avaliacoes/avaliacoes-prestador")
    suspend fun getAvaliacoes(): Response<List<Avaliacao>>

    @GET("/atividades-pedido/atividade-pedido-por-prestador")
    suspend fun getAtividades(): Response<List<Atividade>>

    @POST("/pedidos/finalizarPedido/{codigoPedido}")
    suspend fun finalizarPedido(@Path("codigoPedido") codigoPedido: String): Response<Pedido>

    @POST("/pedidos/cancelarPedido/{codigoPedido}")
    suspend fun cancelarPedido(@Path("codigoPedido") codigoPedido: String): Response<Pedido>

    @PATCH("/pedidos/atualizarPedidoV2")
    suspend fun atualizarPedido(@Body pedido: PedidoCriacao, @Query("idPedido") pedidoId: Int): Response<Pedido>

    @PUT("/usuarios/perfil/{cpf}")
    suspend fun atualizarPerfil(@Body usuario: UsuarioAtualizacao, @Path("cpf") cpf: String): Response<Usuario>

}