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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    val servicoSelectionState by pedidosViewModel.servicoSelectionState.observeAsState(emptyMap())
    val prestadorSelectionState by pedidosViewModel.prestadorSelectionState.observeAsState(emptyMap())
    var produtoSelectionState by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    LaunchedEffect(codigo) {
        pedidosViewModel.getPedidoPorCodigo(codigo)
        servicoViewModel.getServicos()
        produtoViewModel.getProdutos()
        prestadorViewModel.getPrestadores()
    }

    LaunchedEffect(pedido) {
        pedido?.let {
            pedidosViewModel.carregarEstadosDeServico(it, servicoViewModel.servicos)

            // Inicializa o estado de seleção dos produtos com base no pedido atual
            produtoSelectionState = produtoViewModel.produtos.associate { produto ->
                val quantidade = it.pedidoProdutos.find { pedidoProduto ->
                    pedidoProduto.nomeProduto == produto.nome
                }?.quantidade ?: 0

                produto.id to quantidade
            }

            pedidosViewModel.carregarEstadosDePrestador(it, prestadorViewModel.prestadores)
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
                pedido?.let {
                    val nomeDividido by remember { mutableStateOf(separarNomeCompleto(it.nomeCliente)) }
                    CustomTextField(
                        label = stringResource(R.string.label_nome),
                        "${nomeDividido.first} ${nomeDividido.second}",
                        onChange = { novoValor ->
                            pedidosViewModel.atualizarAtributoCliente { cliente ->
                                cliente.copy(nome = novoValor)
                            }
                        }
                    )
                    CustomTextField(
                        label = stringResource(R.string.label_email),
                        it.emailCliente,
                        onChange = { novoValor ->
                            pedidosViewModel.atualizarAtributoCliente { cliente ->
                                cliente.copy(nome = novoValor)
                            }
                        }
                    )
                    CustomTextField(
                        label = stringResource(R.string.label_telefone),
                        it.telefoneCliente,
                        onChange = { novoValor ->
                            pedidosViewModel.atualizarAtributoCliente { cliente ->
                                cliente.copy(nome = novoValor)
                            }
                        }
                    )
                    CustomTextField(
                        label = stringResource(R.string.label_cpf),
                        it.cpfCliente,
                        onChange = { novoValor ->
                            pedidosViewModel.atualizarAtributoCliente { cliente ->
                                cliente.copy(nome = novoValor)
                            }
                        }
                    )

                    var selectedForma = when (pedido!!.formaPagamentoEnum) {
                        "1" -> "Cartão de crédito"
                        "2" -> "Cartão de débito"
                        "3" -> "Dinheiro"
                        "4" -> "PIX"
                        else -> ""
                    }
                    FormaPagamentoDropdown(
                        selectedForma = selectedForma,
                        onFormaSelected = { forma -> selectedForma = forma
                            pedidosViewModel.atualizarFormaPagamento(selectedForma)
                        }
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
                // Mapa para armazenar o prestador selecionado por serviço

                servicoViewModel.servicos.forEach { servico ->
                    Column(verticalArrangement = Arrangement.Center) {
                        val isChecked = servicoSelectionState[servico.id] ?: false

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isChecked, // Estado da checkbox para o serviço
                                onCheckedChange = { checked ->
                                    // Atualiza o estado no ViewModel
                                    pedidosViewModel.atualizarServicoSelecionado(servico.id, checked)

                                    // Remove ou redefine os prestadores vinculados ao serviço, conforme necessário
                                    pedidosViewModel.atualizarPedidoPrestador(servico.id, if (checked) null else null)
                                }
                            )
                            Text(
                                text = servico.descricaoServico,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(150.dp)
                            )
                        }

                        if (isChecked) {
                            val pedidoPrestador = pedido?.pedidoPrestador?.find { it.idServico == servico.id } // ID do prestador associado ao serviço
                            val prestadorAtual = pedidoPrestador.let { prestadorSelectionState[it?.idPrestador] }

                            Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                                PrestadorDropdown(
                                    selectedPrestador = prestadorAtual,  // Passa o prestador encontrado, não o ID
                                    onPrestadorSelected = { selectedPrestador ->
                                        // Atualiza o prestador selecionado para o serviço
                                        pedidosViewModel.atualizarPrestadorParaServico(
                                            servico.id,
                                            selectedPrestador.id
                                        )

                                        // Atualiza a associação entre o prestador e o serviço
                                        pedidosViewModel.atualizarPedidoPrestador(
                                            servico.id,
                                            selectedPrestador.id
                                        )
                                    },
                                    prestadorViewModel = prestadorViewModel
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                produtoViewModel.produtos.forEach { produto ->
                    val quantidade = produtoSelectionState[produto.id] ?: 0
                    CustomAddOrRemoveInput(
                        produto = produto.nome,
                        quantidade = quantidade,
                        onQuantidadeChanged = { novaQuantidade ->
                            produtoSelectionState = produtoSelectionState.toMutableMap().apply {
                                this[produto.id] = novaQuantidade
                            }
                            pedidosViewModel.atualizarPedidoProduto(produto.id, novaQuantidade)
                        }
                    )
                }
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
                    onClick = {
                        pedido?.let { pedidosViewModel.atualizarPedidoCriacao(it.idPedido) }

                    },
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
fun CustomTextField(label: String, valueParam: String, onChange: (String) -> Unit) {
    var value by remember { mutableStateOf(valueParam) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onChange(it) // Chama a função para atualizar o valor na ViewModel
        },
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}


@Composable
fun CustomAddOrRemoveInput(
    produto: String,
    quantidade: Int,
    onQuantidadeChanged: (Int) -> Unit // Callback para atualizar a quantidade
) {
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
                shape = RoundedCornerShape(20)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(210.dp)
                .padding(horizontal = 8.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(20)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "$produto ($quantidadeTotal)")
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E28AC)),
            onClick = {
                if (quantidadeTotal > 0) {
                    quantidadeTotal--
                    onQuantidadeChanged(quantidadeTotal)
                }
            }
        ) {
            Text("-", fontSize = 20.sp)
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E28AC)),
            onClick = {
                quantidadeTotal++
                onQuantidadeChanged(quantidadeTotal)
            }
        ) {
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
                        val pagamentoEnum = when (formaPagamento) {
                            "Crédito" -> "1"
                            "Débito" -> "2"
                            "Dinheiro" -> "3"
                            "PIX" -> "4"
                            else -> ""
                        }
                        onFormaSelected(pagamentoEnum) // Atualiza com o valor correto
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
    selectedPrestador: Prestador?,
    onPrestadorSelected: (Prestador) -> Unit,
    prestadorViewModel: PrestadorViewModel,
) {
    // Carrega a lista de prestadores
    val prestadores = remember { prestadorViewModel.prestadores }

    // Estado para controlar o menu dropdown
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedPrestador?.nome ?: "") }

    // Atualiza o valor do selectedText caso selectedPrestador mude
    LaunchedEffect(selectedPrestador) {
        selectedText = selectedPrestador?.nome ?: ""
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Prestador") },
            textStyle = TextStyle(fontSize = 20.sp),
            singleLine = true,
            modifier = Modifier
                .width(300.dp)
                .clickable { expanded = true }
                .padding(vertical = 8.dp)
                .height(60.dp),
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
                .height(200.dp)
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



fun separarNomeCompleto(nomeCompleto: String): Pair<String, String> {
    // Divide o nome completo em partes
    val partes = nomeCompleto.split(" ")

    return if (partes.size > 1) {
        // O primeiro elemento é o nome e o último elemento é o sobrenome
        Pair(partes.first(), partes.last())
    } else {
        // Se não houver sobrenome, retorna o nome como o primeiro elemento e uma string vazia como sobrenome
        Pair(partes.first(), "")
    }
}

