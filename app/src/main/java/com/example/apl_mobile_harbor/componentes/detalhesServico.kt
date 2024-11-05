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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.classes.pedido.formatarCpf
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun ServiceDetailScreen(
    navController: NavHostController,
    codigo: String,
    pedidosViewModel: PedidosViewModel = koinViewModel()
) {
    val pedido by pedidosViewModel.pedidoAtual.observeAsState()
    var showConfirmacaoModal by remember { mutableStateOf(false) }
    var modalIsPositive by remember { mutableStateOf(false) }

    LaunchedEffect(codigo) {
        pedidosViewModel.getPedidoPorCodigo(codigo)
    }

    if (showConfirmacaoModal) {
        pedido?.let {
            ConfirmacaoModal(
                modalIsPositive,
                it.nomeCliente,
                it.pedidoPrestador[0].descricaoServico,
                onDismissRequest = {
                    showConfirmacaoModal = false
                },
                onConfirm = {
                    if (modalIsPositive) {
                        pedidosViewModel.finalizarPedido(it)
                    } else {
                        pedidosViewModel.cancelarPedido(it)
                    }
                    showConfirmacaoModal = false
                    navController.popBackStack()
                }
            )
        }
    }

    Column {
        pedido?.let { TopBar(title = it.nomeCliente, navController) }


        // Conteúdo rolável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            pedido?.let { pedido ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${pedido.let { convertDateStringToFormattedDate(it.dataAgendamento) }} | ${
                            pedido.pedidoPrestador.getOrNull(0)?.let { formatDate(it.dataInicio) }}",
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionButton(
                        icon = R.drawable.check,
                        text = stringResource(R.string.dar_baixa),
                        onClick = {
                            modalIsPositive = true
                            showConfirmacaoModal = true
                        }
                    )
                    ActionButton(
                        icon = R.drawable.cancel,
                        text = stringResource(R.string.cancelar),
                        onClick = {
                            modalIsPositive = false
                            showConfirmacaoModal = true
                        }
                    )
                    ActionButton(
                        icon = R.drawable.edit,
                        text = stringResource(R.string.editar),
                        onClick = {
                            navController.navigate("editarPedidoScreen/${pedido.codigoPedido}")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.detalhes_cliente),
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(8.dp)) {
                    InfoRow(label = stringResource(R.string.label_nome) + ":", info = pedido.nomeCliente)
                    InfoRow(label = stringResource(R.string.label_email) + ":", info = pedido.emailCliente)
                    InfoRow(label = stringResource(R.string.label_telefone) + ":", info = pedido.telefoneCliente)
                    InfoRow(label = stringResource(R.string.label_cpf) + ":", info = formatarCpf(pedido.cpfCliente))

                    val pagamento = when (pedido.formaPagamentoEnum) {
                        "1" -> "Cartão de crédito"
                        "2" -> "Cartão de débito"
                        "3" -> "Dinheiro"
                        "4" -> "PIX"
                        else -> "Forma não anunciada"
                    }
                    InfoRow(label = stringResource(R.string.label_metodo_pagamento) + ":", info = pagamento)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.detalhes_servico_produto),
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                    text = stringResource(R.string.detalhes_servico),
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )

                pedido.pedidoPrestador.forEach { servico ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = servico.descricaoServico, color = Color.DarkGray)
                        Text(text = "R$${String.format(Locale("pt", "BR"),"%.2f", servico.valorServico).replace(".", ",")}", color = Color.DarkGray)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    color = Color.LightGray, // Cor da linha
                    thickness = 1.dp,        // Espessura da linha
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                    text = stringResource(R.string.detalhes_produto),
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )

                if (pedido.pedidoProdutos.isEmpty()) {
                    Text(
                        text = stringResource(R.string.sem_produtos),
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    pedido.pedidoProdutos.forEach { produto ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${produto.quantidade}x ${produto.nomeProduto}", color = Color.DarkGray)
                            Text(text = "R$${String.format(Locale("pt", "BR"),"%.2f", produto.valorProduto * produto.quantidade).replace(".", ",")}", color = Color.DarkGray)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    color = Color.LightGray, // Cor da linha
                    thickness = 1.dp,        // Espessura da linha
                    modifier = Modifier.padding(8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.total_pagar),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "R$${String.format(Locale("pt", "BR"),"%.2f", pedido.totalPedido).replace(".", ",")}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            }
        }
    }
}





@Composable
fun ActionButton(icon: Int, text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .size(width = 100.dp, height = 150.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            color = Color.DarkGray,
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun InfoRow(label: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.DarkGray,
            modifier = Modifier.weight(0.3f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(50.dp))
        Text(
            modifier = Modifier.weight(0.7f),
            text = info,
            color = Color.DarkGray,
            textAlign = TextAlign.Right,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}