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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.view_models.avaliacao.AvaliacaoViewModel
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AvaliacaoScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(stringResource(R.string.label_minhas_avaliacoes), navController)
        AvaliacaoInfo()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AvaliacaoInfo(
    avaliacaoViewModel: AvaliacaoViewModel = koinViewModel(),
    pedidosViewModel: PedidosViewModel = koinViewModel()
) {
    avaliacaoViewModel.getAvaliacoes()
    LazyColumn(modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)) {
        items(avaliacaoViewModel.avaliacoesPrestador.toList()) { avaliacao ->
            pedidosViewModel.getPedidoPorCodigo(avaliacao.codigoPedido)

            val pedido = pedidosViewModel.pedidoAtual.value
            if (pedido != null) {
                AvaliacaoItem(
                    name = avaliacao.nomeCliente,
                    time = formatDate(
                        pedido.pedidoPrestador[0].dataInicio,
                        pedido.pedidoPrestador.last().dataFim
                    ),
                    service = pedido.pedidoPrestador[0].descricaoServico,
                    avaliacao = avaliacao.estrelas
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun AvaliacaoItem(
    name: String,
    time: String,
    service: String,
    avaliacao: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = {
                // Ação ao clicar
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
            Spacer(modifier = Modifier.weight(0.5f))
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                 RatingBar(avaliacao)
            }
        }
    }

}

@Composable
fun RatingBar(rating: Double, modifier: Modifier = Modifier) {
    val filledStars = rating.toInt()
    val unfilledStars = 5 - filledStars
    Row(modifier = modifier) {
        repeat(filledStars) {
            Image(
                painter = painterResource(id = R.drawable.estrela_cheia),
                contentDescription = "Filled star",
                modifier = Modifier.size(24.dp)
            )
        }
        repeat(unfilledStars) {
            Image(
                painter = painterResource(id = R.drawable.estrela_vazia),
                contentDescription = "Unfilled star",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}