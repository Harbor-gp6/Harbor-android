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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.classes.pedido.formatDateOnly
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailScreen(
    navController: NavHostController,
    codigo: String,
    pedidosViewModel: PedidosViewModel = koinViewModel()
) {

    val pedido by pedidosViewModel.pedidoAtual.observeAsState()

    LaunchedEffect(codigo) {
        codigo.let {
            pedidosViewModel.getPedidoPorCodigo(it) // Chama a função que altera `pedidoAtual` na ViewModel
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        pedido?.let { TopBar(title = it.nomeCliente, navController) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Adiciona um pouco de espaço acima e abaixo
            contentAlignment = Alignment.Center // Centraliza o conteúdo
        ) {
            Text(
                text = "${pedido?.let { convertDateStringToFormattedDate(it.dataAgendamento) }} | ${pedido?.pedidoPrestador?.get(0)?.let { formatDate(it.dataInicio) }}",
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Botões de ação
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

                }
            )
            ActionButton(
                icon = R.drawable.cancel,
                text = stringResource(R.string.cancelar),
                onClick = {
                }
            )
            ActionButton(
                icon = R.drawable.edit,
                text = stringResource(R.string.editar),
                onClick = {
                    navController.navigate("editarPedidoScreen")
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


        Column(modifier = Modifier.padding(16.dp)) {
            pedido?.let { InfoRow(label = stringResource(R.string.label_nome) + ":", info = it.nomeCliente) }
            pedido?.let { InfoRow(label = stringResource(R.string.label_email) + ":", info = it.emailCliente) }
            pedido?.let { InfoRow(label = stringResource(R.string.label_telefone) + ":", info = it.telefoneCliente) }
            pedido?.let { InfoRow(label = stringResource(R.string.label_cpf) + ":", info = it.cpfCliente) }
        }
        Spacer(modifier = Modifier.weight(1f))
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
            maxLines = 1,
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