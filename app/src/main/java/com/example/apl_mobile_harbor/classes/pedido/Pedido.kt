package com.example.apl_mobile_harbor.classes.pedido

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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
    val idPrestador: Int,
    val idServico: Int,
    val nomePrestador: String,
    val descricaoServico: String,
    val dataInicio: String,
    val dataFim: String,
    val valorServico: Double
)

data class PedidoProduto(
    val idProduto: Int,
    val nomeProduto: String,
    val quantidade: Int,
    val valorProduto: Double
)

fun convertToDate(date: String): LocalDateTime? {
    // Use o formato correto de acordo com as strings que você espera
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    return try {
        val dataFormatada = LocalDateTime.parse(date, formatter)
        dataFormatada
    } catch (e: Exception) {
        Log.e("string", "Erro ao converter", e)
        null
    }

}

fun formatDateOnly(date: String): String {
    // Aqui você pode aplicar a lógica para formatar a data como "dd/MM/yyyy" ou outro formato desejado
    val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(parsedDate ?: Date())
}

fun formatDate(date: String): String {
    // Use o formato correto de acordo com as strings que você espera
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    return try {
        val inicioFormatado = LocalDateTime.parse(date, formatter)

        // Formatar a saída para mostrar apenas a parte que você deseja
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        inicioFormatado.format(outputFormatter)
    } catch (e: Exception) {
        // Tratar a exceção e retornar uma mensagem apropriada
        "Erro ao formatar datas: ${e.message}"
    }
}

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

fun formatarCpf(cpf: String): String {
    // Remove caracteres não numéricos
    val numeros = cpf.replace(Regex("[^0-9]"), "")

    return when {
        numeros.length < 3 -> numeros
        numeros.length < 6 -> "${numeros.substring(0, 3)}.${numeros.substring(3)}"
        numeros.length < 9 -> "${numeros.substring(0, 3)}.${numeros.substring(3, 6)}.${numeros.substring(6)}"
        numeros.length == 11 -> "${numeros.substring(0, 3)}.${numeros.substring(3, 6)}.${numeros.substring(6, 9)}-${numeros.substring(9)}"
        else -> cpf // Retorna o CPF original se não for válido
    }
}