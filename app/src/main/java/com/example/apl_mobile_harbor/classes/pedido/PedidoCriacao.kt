package com.example.apl_mobile_harbor.classes.pedido

data class PedidoCriacao(
    val cliente: Cliente,
    val cnpjEmpresa: String,
    val pedidoPrestador: List<PedidoPrestadorCriacao>,
    val pedidoProdutos: List<PedidoProdutoCriacao>,
    val dataAgentamento: String,
    val formaPagamentoEnum: String
)

data class Cliente(
    val nome: String,
    val sobrenome: String,
    val telefone: String,
    val cpf: String,
    val email: String
)

data class PedidoPrestadorCriacao(
    val prestadorId: Int,
    val servicoId: Int
)

data class PedidoProdutoCriacao(
    val id: Int,
    val quantidade: Int
)
