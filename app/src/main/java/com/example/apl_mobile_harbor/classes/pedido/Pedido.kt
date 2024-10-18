package com.example.apl_mobile_harbor.classes.pedido

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Pedido(
    val idPedido: Int,
    val nomeCliente: String,
    val emailCliente: String,
    val cpfCliente: String,
    val telefoneCliente: String,
    val pedidoPrestador: List<PedidoPrestador>,
    val pedidoProdutos: List<PedidoProduto>,
    val dataCriacao: String,
    val dataAgendamento: String,
    val formaPagamentoEnum: String,
    val codigoPedido: String,
    val statusPedidoEnum: String,
    val totalPedido: Double
)

data class PedidoPrestador(
    val nomePrestador: String,
    val descricaoServico: String,
    val dataInicio: String,
    val dataFim: String
)

data class PedidoProduto(
    val nomeProduto: String,
    val quantidade: Int
)

fun formatDate(date1: String, date2: String): String {
    // Use o formato correto de acordo com as strings que você espera
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    return try {
        val inicioFormatado = LocalDateTime.parse(date1, formatter)
        val fimFormatado = LocalDateTime.parse(date2, formatter)

        // Formatar a saída para mostrar apenas a parte que você deseja
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        "${inicioFormatado.format(outputFormatter)} - ${fimFormatado.format(outputFormatter)}"
    } catch (e: Exception) {
        // Tratar a exceção e retornar uma mensagem apropriada
        "Erro ao formatar datas: ${e.message}"
    }
}