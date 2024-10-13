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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R

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
        StaticCalendarRow()
        ServicoFilter()
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
            Text(
                text = "Dez, 2024",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0E28AC)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(id = R.drawable.seta_baixo),
                contentDescription = null,
                modifier = Modifier.size(23.dp)
            )
            Spacer(modifier = Modifier.width(80.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E28AC)),
                modifier = Modifier
                    .offset(y = (-8).dp)
                    .size(width = 170.dp, height = 35.dp),
                shape = RoundedCornerShape(8.dp)
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
fun StaticCalendarRow() {
    Row(
        modifier = Modifier
            .offset(y = (-10).dp)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "D",
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "19",
                color = Color.LightGray,
                fontSize = 16.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "S",
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "20",
                color = Color.LightGray,
                fontSize = 16.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "T",
                color = Color(0xFF0E28AC),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "21",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .size(width = 40.dp, height = 60.dp)
                    .background(
                        color = Color(0xFF0E28AC),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Q",
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .offset(y = (-10).dp)
                )
                Text(
                    text = "22",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .offset(y = (9).dp)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Q",
                color = Color(0xFF0E28AC),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "23",
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        // Sexto dia (Desabilitado)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "S",
                color = Color(0xFF0E28AC),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "24",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "S",
                color = Color(0xFF0E28AC),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "25",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ServicoFilter() {
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
        Image(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = "Filtro",
            modifier = Modifier.size(23.dp)
        )
    }
}

@Composable
fun Servicos() {
    Row(
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .offset(y = (-40).dp),
            text = stringResource(R.string.horario_agendamento, "9:00"),
            fontSize = 17.sp,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .offset(x = 80.dp, y = -20.dp)
                .size(width = 250.dp, height = 60.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.servicos_agendados_card, "Corte de Cabelo"),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x = 10.dp, y = 2.dp)
            )
            Image(
                modifier = Modifier
                    .offset(x = 225.dp, y = 5.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.config),
                contentDescription = "Configurações",
            )
            Image(
                modifier = Modifier
                    .offset(x = 10.dp, y = 25.dp)
                    .size(23.dp),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Usuário",
            )
            Text(
                modifier = Modifier
                    .offset(x = 40.dp, y = 28.dp),
                text = "Vitor Ramos",
                fontSize = 15.sp,
                color = Color.Black
            )
            Image(
                modifier = Modifier
                    .offset(x = 225.dp, y = 35.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.confirmado),
                contentDescription = "Confirmado",
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .offset(y = (-30).dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-60).dp),
            text = stringResource(R.string.horario_agendamento, "9:30"),
            fontSize = 17.sp,
            color = Color.LightGray
        )
    }

    //Serviço 2

    Row(
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .offset(y = (-100).dp),
            text = stringResource(R.string.horario_agendamento, "12:00"),
            fontSize = 17.sp,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .offset(x = 70.dp, y = -80.dp)
                .size(width = 250.dp, height = 60.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.servicos_agendados_card, "Barba"),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x = 10.dp, y = 2.dp)
            )
            Image(
                modifier = Modifier
                    .offset(x = 225.dp, y = 5.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.config),
                contentDescription = "Configurações",
            )
            Image(
                modifier = Modifier
                    .offset(x = 10.dp, y = 25.dp)
                    .size(23.dp),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Usuário",
            )
            Text(
                modifier = Modifier
                    .offset(x = 40.dp, y = 28.dp),
                text = stringResource(R.string.servicos_agendados_card, "Guilherme Nascimento"),
                fontSize = 15.sp,
                color = Color.Black
            )
            Image(
                modifier = Modifier
                    .offset(x = 225.dp, y = 35.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.confirmado),
                contentDescription = "Confirmado",
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .offset(y = (-30).dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-120).dp),
            text = stringResource(R.string.horario_agendamento, "12:30"),
            fontSize = 17.sp,
            color = Color.LightGray
        )
    }
    // Serviço 3

    Row(
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .offset(y = (-150).dp),
            text = stringResource(R.string.horario_agendamento, "14:00"),
            fontSize = 17.sp,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .offset(x = 70.dp, y = -130.dp)
                .size(width = 250.dp, height = 60.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.servicos_agendados_card, "Lavar a cabeça"),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x = 10.dp, y = 2.dp)
            )
            Image(
                modifier = Modifier
                    .offset(x = 225.dp, y = 5.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.config),
                contentDescription = "Configurações",
            )
            Image(
                modifier = Modifier
                    .offset(x = 10.dp, y = 25.dp)
                    .size(23.dp),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Usuário",
            )
            Text(
                modifier = Modifier
                    .offset(x = 40.dp, y = 28.dp),
                text = stringResource(R.string.servicos_agendados_card, "Luka Caetano"),
                fontSize = 15.sp,
                color = Color.Black
            )
            Image(
                modifier = Modifier
                    .offset(x = 225.dp, y = 35.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.confirmado),
                contentDescription = "Confirmado",
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .offset(y = (-30).dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-170).dp),
            text = stringResource(R.string.horario_agendamento, "14:30"),
            fontSize = 17.sp,
            color = Color.LightGray
        )
    }
}