package com.example.apl_mobile_harbor.componentes

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.classes.prestador.Prestador
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import com.example.apl_mobile_harbor.view_models.prestador.PrestadorViewModel
import com.example.apl_mobile_harbor.view_models.produto.ProdutoViewModel
import com.example.apl_mobile_harbor.view_models.servico.ServicoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditarServicoScreen(
    navController: NavHostController,
    codigo: String,
    pedidosViewModel: PedidosViewModel = koinViewModel(),
    servicoViewModel: ServicoViewModel = koinViewModel(),
    produtoViewModel: ProdutoViewModel = koinViewModel(),
    prestadorViewModel: PrestadorViewModel = koinViewModel()
) {
    val pedido by pedidosViewModel.pedidoAtual.observeAsState()
    var servicoSelectionState by remember { mutableStateOf<Map<String, Boolean>>(emptyMap()) }
    var produtoSelectionState by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    LaunchedEffect(codigo) {
        pedidosViewModel.getPedidoPorCodigo(codigo)
        servicoViewModel.getServicos()
        produtoViewModel.getProdutos()
        prestadorViewModel.getPrestadores()
    }

    LaunchedEffect(pedido) {
        pedido?.let {
            servicoSelectionState = servicoViewModel.servicos.associate { servico ->
                val isChecked = it.pedidoPrestador.any { prestador ->
                    prestador.descricaoServico == servico.descricaoServico
                } ?: false
                servico.descricaoServico to isChecked
            }
            produtoSelectionState = produtoViewModel.produtos.associate { produto ->
                val quantidade = it.pedidoProdutos.find { pedidoProduto ->
                    pedidoProduto.nomeProduto == produto.nome // Supondo que você tenha um ID para identificar o produto
                }?.quantidade ?: 0

                produto.id to quantidade // Aqui estamos usando o ID do produto como chave
            }
        }
    }

    Column(
        Modifier
            .background(Color(0xFFF5F5F5))
    ) {
        pedido?.let { TopBar(it.nomeCliente, navController) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${pedido?.let { convertDateStringToFormattedDate(it.dataAgendamento) }} | ${
                        pedido?.pedidoPrestador?.getOrNull(0)?.let { formatDate(it.dataInicio) }
                    }",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botões de ação
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

            }

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_detalhes_cliente),
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                pedido?.let { CustomTextField(label = stringResource(R.string.label_nome), it.nomeCliente) }
                pedido?.let { CustomTextField(label = stringResource(R.string.label_email), it.emailCliente) }
                pedido?.let { CustomTextField(label = stringResource(R.string.label_telefone), it.telefoneCliente) }
                pedido?.let { CustomTextField(label = stringResource(R.string.label_cpf), it.cpfCliente) }
                pedido?.let {
                    var selectedForma = when (pedido!!.formaPagamentoEnum) {
                        "1" -> "Cartão de crédito"
                        "2" -> "Cartão de débito"
                        "3" -> "Dinheiro"
                        "4" -> "PIX"
                        else -> ""
                    }
                    FormaPagamentoDropdown(
                        selectedForma = selectedForma,
                        onFormaSelected = { forma -> selectedForma = forma }
                    )
                }
            }

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

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                servicoViewModel.servicos.forEach({ servico ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val isChecked = servicoSelectionState[servico.descricaoServico] ?: false
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                servicoSelectionState = servicoSelectionState.toMutableMap().apply {
                                    this[servico.descricaoServico] = checked
                                }
                            }
                        )
                        Text(text = servico.descricaoServico)

                    }
                })

            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                produtoViewModel.produtos.forEach({ produto ->
                    val quantidade = produtoSelectionState[produto.id] ?: 0
                    CustomAddOrRemoveInput(produto.nome, quantidade)
                })
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0E28AC)
                    )
                ) {
                    Text(text = stringResource(R.string.botao_cancelar), color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0E28AC)
                    )
                ) {
                    Text(text = stringResource(R.string.botao_salvar), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun CustomTextField(label: String, valueParam: String) {
    var value by remember { mutableStateOf(valueParam) }
    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun CustomAddOrRemoveInput(produto: String, quantidade: Int) {
    var quantidadeTotal by remember { mutableStateOf(quantidade) }

    LaunchedEffect(quantidade) {
        quantidadeTotal = quantidade
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(20)),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(210.dp)
                .padding(horizontal = 8.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(20)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "$produto ($quantidadeTotal)"
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC)
            ),
            onClick = {
            if (quantidadeTotal > 0) {
                quantidadeTotal--
            }
        }) {
            Text("-", fontSize = 20.sp)
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC)
            ),
            onClick = {
            quantidadeTotal++
        }) {
            Text("+", fontSize = 20.sp)
        }
    }
}

@Composable
fun FormaPagamentoDropdown(
    selectedForma: String,
    onFormaSelected: (String) -> Unit
) {
    // Lista de opções de formas de pagamento
    val formasPagamento = listOf("Crédito", "Débito", "Dinheiro", "PIX")

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedForma) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Forma de Pagamento") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 8.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.seta_baixo), // ícone de seta
                    contentDescription = "Expandir Menu",
                    modifier = Modifier.clickable { expanded = !expanded }
                        .size(10.dp)
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            formasPagamento.forEach { formaPagamento ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = formaPagamento
                        onFormaSelected(formaPagamento)
                        expanded = false
                    },
                    text = { Text(formaPagamento) }
                )
            }
        }
    }
}

@Composable
fun PrestadorDropdown(
    selectedPrestador: Prestador,
    onPrestadorSelected: (Prestador) -> Unit,
    prestadorViewModel: PrestadorViewModel
) {
    // Lista de opções de formas de pagamento
    val prestadores = remember { mutableStateListOf(*prestadorViewModel.prestadores.toTypedArray()) }

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedPrestador.nome) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Forma de Pagamento") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 8.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.seta_baixo), // ícone de seta
                    contentDescription = "Expandir Menu",
                    modifier = Modifier.clickable { expanded = !expanded }
                        .size(10.dp)
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            prestadores.forEach { prestador ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = prestador.nome
                        onPrestadorSelected(prestador)
                        expanded = false
                    },
                    text = { Text(prestador.nome) }
                )
            }
        }
    }
}
