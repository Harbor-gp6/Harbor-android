package com.example.apl_mobile_harbor.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.pedido.Pedido
import com.example.apl_mobile_harbor.classes.pedido.convertToDate
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.classes.pedido.formatDateOnly
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

@Composable
fun PedidoScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(stringResource(R.string.label_meus_servicos), navController)
        ServiceInfo(navController = navController)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceInfo(
    pedidosViewModel: PedidosViewModel = koinViewModel(),
    navController: NavHostController
) {
    // Atualiza a lista de pedidos no ViewModel
    pedidosViewModel.atualizarListaDePedidos()

    // Agrupa os pedidos por data e ordena as datas
    var showConfirmacaoModal by remember { mutableStateOf(false) }
    var modalIsPositive by remember { mutableStateOf(false) }
    val pedidosAgrupados = pedidosViewModel.pedidos.groupBy { it.pedidoPrestador[0].dataInicio }
    val datasOrdenadas = pedidosAgrupados.keys.sortedBy { convertToDate(it) }
    var pedidoSelecionado by remember { mutableStateOf<Pedido?>(null) }

    LazyColumn(modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)) {
        // Exibe as datas ordenadas e os pedidos de cada dia
        datasOrdenadas.forEach { dataAgendamento ->
            val pedidosDoDia = pedidosAgrupados[dataAgendamento] ?: emptyList()

            // Exibe a data como título
            item {
                Text(
                    text = formatDateOnly(dataAgendamento), // Formata para exibir apenas a data
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    color = Color.Black
                )
            }

            // Exibe os pedidos daquele dia
            items(pedidosDoDia, key = { it.idPedido }) { pedido ->
                val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

                LaunchedEffect(swipeToDismissBoxState.currentValue) {
                    if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
                        modalIsPositive = true
                        showConfirmacaoModal = true
                    }
                    if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        modalIsPositive = false
                        showConfirmacaoModal = true
                    }
                    pedidoSelecionado = pedido
                }

                LaunchedEffect(showConfirmacaoModal) {
                    if (!showConfirmacaoModal) {
                        swipeToDismissBoxState.snapTo(SwipeToDismissBoxValue.Settled)
                    }
                }

                if (showConfirmacaoModal && pedidoSelecionado == pedido) {
                    ConfirmacaoModal(
                        modalIsPositive,
                        pedido.nomeCliente,
                        pedido.pedidoPrestador[0].descricaoServico,
                        onDismissRequest = { showConfirmacaoModal = false },
                        onConfirm = {
                            if (modalIsPositive) {
                                pedidosViewModel.finalizarPedido(pedido)
                            } else {
                                pedidosViewModel.cancelarPedido(pedido)
                            }
                            showConfirmacaoModal = false
                        }
                    )
                }

                SwipeToDismissBox(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    backgroundContent = {
                        when(swipeToDismissBoxState.dismissDirection) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFFA7FFB4))
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .size(70.dp)
                                            .padding(16.dp)
                                            .align(Alignment.CenterStart),
                                        painter = painterResource(R.drawable.check),
                                        contentDescription = "check")
                                }
                            }
                            SwipeToDismissBoxValue.EndToStart -> {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFFFFBFBF))
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .size(70.dp)
                                            .padding(16.dp)
                                            .align(Alignment.CenterEnd),
                                        painter = painterResource(R.drawable.cancel),
                                        contentDescription = "cancel",
                                    )
                                }
                            }
                            SwipeToDismissBoxValue.Settled -> {}
                        }
                    },
                    state = swipeToDismissBoxState
                ) {
                    ContactItem(
                        codigoPedido = pedido.codigoPedido,
                        name = pedido.nomeCliente,
                        time = formatDate(
                            pedido.pedidoPrestador[0].dataInicio,
                            pedido.pedidoPrestador.last().dataFim
                        ),
                        service = pedido.pedidoPrestador[0].descricaoServico,
                        formaPagamento = pedido.formaPagamentoEnum,
                        total = pedido.totalPedido,
                        navController
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun ContactItem(
    codigoPedido: String,
    name: String,
    time: String,
    service: String,
    formaPagamento: String,
    total: Double,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = {
                navController.navigate("detalhesPedidoScreen/$codigoPedido")
            })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Spacer(modifier = Modifier.width(16.dp)) // Espaço à esquerda
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1.5f) // Para ocupar o espaço restante
                    .padding(vertical = 4.dp)
            ) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0E28AC))
                Text(text = time, fontSize = 14.sp, color = Color.Gray)
                Text(text = service, fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.weight(0.5f)) // Espaço para empurrar a coluna da direita
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.End // Alinhamento da coluna à direita
            ) {
                val pagamento = when (formaPagamento) {
                    "1" -> "Crédito"
                    "2" -> "Débito"
                    "3" -> "Dinheiro"
                    "4" -> "PIX"
                    else -> "Forma não anunciada"
                }
                Text(text = "R$${formatDouble(total)}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = pagamento, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }

}

fun formatDouble(value: Double): String {
    // Cria um formatador para o Brasil
    val formatter = NumberFormat.getInstance(Locale("pt", "BR"))
    // Define o número de casas decimais
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    // Retorna o valor formatado
    return formatter.format(value)
}