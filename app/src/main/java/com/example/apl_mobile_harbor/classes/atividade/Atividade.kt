package com.example.apl_mobile_harbor.classes.atividade

import com.example.apl_mobile_harbor.classes.pedido.Pedido
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Atividade(
    val id: String,
    val statusPedidoEnum: String,
    val dataCriacao: String,
    val codigoPedido: String,
    val cnpjEmpresa: String,
    val cpfs: List<String>,
    val nomeCliente: String,
    val servico: String,

    var pedido: Pedido? = null
)

fun formatDateToActivity(inputDate: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Ajuste o padrão conforme necessário
        val parsedDate = LocalDate.parse(inputDate, formatter)
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        when (parsedDate) {
            today -> "hoje"
            tomorrow -> "amanhã"
            else -> parsedDate.format(DateTimeFormatter.ofPattern("dd/MM"))
        }
    } catch (e: DateTimeParseException) {
        "Data inválida"
    }
}
