package com.example.apl_mobile_harbor.componentes

import android.content.Intent
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.AgendaActivity
import com.example.apl_mobile_harbor.activities.perfil.PerfilActivity
import com.example.apl_mobile_harbor.R

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
            MenuCard(
                R.string.card_agenda, R.drawable.calendar_month, Modifier.weight(1f),
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
            MenuCard(
                R.string.card_servicos, R.drawable.person_add, Modifier.weight(1f),
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
                Text(text = stringResource(
                    R.string.data_hora_pedido,
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
        MenuItems(
            R.drawable.calendar_month, R.string.pagina_agenda,
            navController.navigate("agendaScreen")
        )
        MenuItems(
            R.drawable.account_circle, R.string.menu_lateral_perfil,
            navController.navigate("agendaScreen")
        )
        MenuItems(
            R.drawable.content_cut, R.string.card_servicos,
            navController.navigate("agendaScreen")
        )
        MenuItems(
            R.drawable.orders, R.string.todos_os_pedidos,
            navController.navigate("agendaScreen")
        )
        MenuItems(
            R.drawable.star, R.string.barra_navegacao_avaliacoes,
            navController.navigate("agendaScreen")
        )
        MenuItems(
            R.drawable.move_item, R.string.sair,
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