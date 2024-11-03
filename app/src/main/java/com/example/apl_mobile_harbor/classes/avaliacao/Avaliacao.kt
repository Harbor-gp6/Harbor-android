package com.example.apl_mobile_harbor.classes.avaliacao

data class Avaliacao(
    val id: Int,
    val nomePrestador: String,
    val nomeCliente: String,
    val codigoPedido: String,
    val estrelas: Double,
    val comentario: String,
    val cnpjEmpresa: String,
    val idCliente: Int
)
