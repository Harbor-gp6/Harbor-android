package com.example.apl_mobile_harbor

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun Header(onMenuClick: () -> Unit, message: String, isHomeActivity: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isHomeActivity) {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Text(
            text = message,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A65DF)
        )
        Icon(
            painter = painterResource(id = R.drawable.tiago),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun TopBar(title: String, navController: NavController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, start = 24.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.seta_esquerda),
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
                .clickable(onClick = { navController.popBackStack() })
        )
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

//Componente Home

@Composable
fun HomeScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            MenuGrid()
            RecentActivities()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun MenuGrid() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuCard(R.string.card_agenda, R.drawable.calendar_month, Modifier.weight(1f),
                weigthLeft = 0.6f,
                weigthRight = 0.4f,
                handleClick = {
                    val intent = Intent(context, AgendaActivity::class.java)
                    context.startActivity(intent)
                }
            )
            MenuCard(title = R.string.card_perfil, R.drawable.finance_mode, Modifier.weight(1f),
                weigthLeft = 0.6f,
                weigthRight = 0.4f,
                handleClick = {
                    val intent = Intent(context, PerfilActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuCard(R.string.card_servicos, R.drawable.person_add, Modifier.weight(1f),
                weigthLeft = 0.8f,
                weigthRight = 0.2f,
                handleClick = {
                }
            )
        }
    }
}

@Composable
fun MenuCard(
    title: Int,
    iconRes: Int,
    modifier: Modifier = Modifier,
    handleClick: () -> Unit,
    weigthLeft: Float,
    weigthRight: Float
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(100.dp)
            .clickable(onClick = handleClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0E28AC)
        ),
        onClick = handleClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(weigthLeft)
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(title),
                            modifier = Modifier
                                .align(Alignment.BottomStart),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
                Box(modifier = Modifier
                    .weight(weigthRight)
                    .clip(RoundedCornerShape(topStart = 30.dp, bottomStart = 50.dp))
                    .background(Color(0xFFD9D9D9))
                    .height(100.dp)
                    .width(50.dp)
                    .padding(start = 15.dp, top = 30.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            tint = Color(0xFF3A65DF),
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RecentActivities() {
    val activities = remember { mutableStateListOf<String>() }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.atividade_recente), fontWeight = FontWeight.Bold)
        LazyColumn {
            items(items = activities.toList()) {
                ActivityItem()
            }
        }
    }
}

@Composable
fun ActivityItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF9DADFF) // Azul para o card
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = stringResource(R.string.pedido_agendado_usuario, "Marcos"), color = Color.Black, fontWeight = FontWeight.Bold)
                Text(text = stringResource(R.string.data_hora_pedido,
                    "Corte de cabelo",
                    "hoje", "17:00"),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
            .background(Color(0xFFD9D9D9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        MenuItems(R.drawable.calendar_month, R.string.pagina_agenda,
            navController.navigate("agendaScreen")
        )
        MenuItems(R.drawable.account_circle, R.string.menu_lateral_perfil,
            navController.navigate("agendaScreen")
        )
        MenuItems(R.drawable.content_cut, R.string.card_servicos,
            navController.navigate("agendaScreen")
        )
        MenuItems(R.drawable.orders, R.string.todos_os_pedidos,
            navController.navigate("agendaScreen")
        )
        MenuItems(R.drawable.star, R.string.barra_navegacao_avaliacoes,
            navController.navigate("agendaScreen")
        )
        MenuItems(R.drawable.move_item, R.string.sair,
            navController.navigate("agendaScreen")
        )
    }
}

@Composable
fun MenuItems(icon: Int, page: Int, navigation: Unit) {
    var color by remember { mutableStateOf(Color.Black) }
    if (icon == R.drawable.move_item) {
        color = Color.Red
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigation })
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFD9D9D9))
                .padding(16.dp)  // Padding interno do Row
                .fillMaxWidth(),  // Garante que o Row preencha a largura disponível
            verticalAlignment = Alignment.CenterVertically,  // Alinha verticalmente
            horizontalArrangement = Arrangement.Start  // Alinha horizontalmente
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)  // Define o tamanho do ícone
            )
            Spacer(modifier = Modifier.width(16.dp))  // Espaço entre ícone e texto
            Text(
                text = stringResource(id = page),
                fontSize = 20.sp,
                color = color
            )
        }
    }
}

// Componente Pedidos

@Composable
fun ServiceScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(stringResource(R.string.label_meus_servicos), navController)
        ServiceInfo()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ServiceInfo() {
    Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)) {
        ContactItem(
            imageRes = R.drawable.user,
            name = "Alex Batista",
            time = "10:30 - 11:00",
            service = "Corte de cabelo"
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactItem(
            imageRes = R.drawable.user,
            name = "José Alves",
            time = "11:00 - 11:30",
            service = "Barba"
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactItem(
            imageRes = R.drawable.user,
            name = "Ben Mendes",
            time = "11:30 - 12:00",
            service = "Corte de cabelo"
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactItem(
            imageRes = R.drawable.user,
            name = "Hugo Pontes",
            time = "12:00 - 12:30",
            service = "Corte de cabelo"
        )
    }
}

@Composable
fun ContactItem(
    imageRes: Int,
    name: String,
    time: String,
    service: String
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = {
                val intent = Intent(context, DetalhesServico::class.java)
                context.startActivity(intent)
            })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0E28AC))
                Text(text = time, fontSize = 14.sp, color = Color.Gray)
                Text(text = stringResource(R.string.label_servico) + ":" + " $service", fontSize = 14.sp, color = Color.Black)
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            IconButton(onClick = {
                val intent = Intent(context, BaixaPositivaActivity::class.java)
                context.startActivity(intent)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.aceitar),
                    contentDescription = "Confirmar",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
            }
            IconButton(onClick = {
                val intent = Intent(context, BaixaNegativaActivity::class.java)
                context.startActivity(intent)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.cancelar),
                    contentDescription = "Negar",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

// Componente Avaliação

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
fun AvaliacaoInfo() {
    Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)) {
        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "Alex Batista",
            time = "09:00 - 09:30"
        )
        Spacer(modifier = Modifier.height(8.dp))

        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "Nunes Filho",
            time = "10:00 - 10:30"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "Ronaldo Reis",
            time = "13:30 - 14:00"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "João Carlos",
            time = "15:30 - 16:00"
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AvaliacaoItem(
    imageRes: Int,
    name: String,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0E28AC)
                )
                Text(text = time, fontSize = 14.sp, color = Color.Gray)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Image(
                            painter = painterResource(id = R.drawable.estrela),
                            contentDescription = "Estrela de avaliação",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "5.0",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

//Componente Agenda

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
    Row(modifier = Modifier
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

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
    Row(modifier = Modifier
        .padding(16.dp)
        .offset(y = (-30).dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Text(
            modifier = Modifier
                .offset(y = (-60).dp),
            text = stringResource(R.string.horario_agendamento, "9:30"),
            fontSize = 17.sp,
            color = Color.LightGray
        )
    }

    //Serviço 2

    Row(modifier = Modifier
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

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
    Row(modifier = Modifier
        .padding(16.dp)
        .offset(y = (-30).dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Text(
            modifier = Modifier
                .offset(y = (-120).dp),
            text = stringResource(R.string.horario_agendamento, "12:30"),
            fontSize = 17.sp,
            color = Color.LightGray
        )
    }
    // Serviço 3

    Row(modifier = Modifier
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

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
    Row(modifier = Modifier
        .padding(16.dp)
        .offset(y = (-30).dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Text(
            modifier = Modifier
                .offset(y = (-170).dp),
            text = stringResource(R.string.horario_agendamento, "14:30"),
            fontSize = 17.sp,
            color = Color.LightGray
        )
    }
}