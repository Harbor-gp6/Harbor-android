package com.example.apl_mobile_harbor.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AgendaScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        TopBar("Agenda", navController)
        AgendaDateSelector()
        Servicos()
    }
}

@Composable
fun AgendaDateSelector() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            DatePickerDocked()
            Spacer(Modifier.width(5.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E28AC)),
                modifier = Modifier
                    .size(width = 170.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.botao_adicionar_servico),
                        color = Color.White
                    )
            }
        }
    }
}

@Composable
fun CardAgenda(
    name: String,
    service: String,
    isFinalizado: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(0.8f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = service,
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = name,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
            Column(
                Modifier
                    .align(Alignment.Top)
                    .padding(top = 20.dp, end = 15.dp),
                horizontalAlignment = Alignment.End
            ) {
                Image(painterResource(R.drawable.pontos), contentDescription = "Pontos", Modifier.size(15.dp))
                Spacer(Modifier.height(35.dp))
                if (isFinalizado) {
                    Image(painterResource(
                        R.drawable.pedido_finalizado),
                        contentDescription = "Finalizado",
                        Modifier.size(20.dp)
                            .align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Composable
fun Servicos(pedidosViewModel: PedidosViewModel = koinViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.label_hora),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.label_servicos_agendados),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    LazyColumn {
        items(items = pedidosViewModel.pedidosPorData.toList()) { pedido ->
            val horaInicio = formatDate(pedido.pedidoPrestador[0].dataInicio)
            val horaFim = formatDate(pedido.pedidoPrestador.last().dataFim)
            Row(Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                Column(
                    Modifier
                        .weight(0.3f)
                ) {
                    Row {
                        Text(text = horaInicio, fontWeight = FontWeight.Bold)
                    }
                    Row {
                        Text(text = horaFim, color = Color(0xFF939393))
                    }
                }
                Column(
                    Modifier
                        .weight(0.7f)
                ) {
                    CardAgenda(
                        pedido.nomeCliente,
                        pedido.pedidoPrestador[0].descricaoServico,
                        pedido.statusPedidoEnum == "Finalizado"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(pedidosViewModel: PedidosViewModel = koinViewModel()) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    val dateToSend = datePickerState.selectedDateMillis?.let { convertToApiDateFormat(it) } ?: ""

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            pedidosViewModel.selectedDate.value = dateToSend
            pedidosViewModel.getPedidosPorData()
            showDatePicker = false
        }
    }

    Box(
        modifier = Modifier
            .padding(2.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Data") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("pt-BR"))
    return formatter.format(Date(millis))
}

fun convertToApiDateFormat(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}