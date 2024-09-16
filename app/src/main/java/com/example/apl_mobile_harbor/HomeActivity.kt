package com.example.apl_mobile_harbor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
                .width(300.dp)
            )
        }
    ) {
        Scaffold(
            topBar = {
                Header { scope.launch { drawerState.open() } }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it)
            ) {
                MenuGrid()
                RecentActivities()
                BottomNavigationBar()
            }
        }
    }
}

@Composable
fun Header(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMenuClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
            )
        }
        Text(
            text = stringResource(R.string.saudacao, "Tiago"),
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
                    val intent = Intent(context, ServicosActivity::class.java)
                    context.startActivity(intent)
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
                                .align(Alignment.BottomCenter),
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.atividade_recente), fontWeight = FontWeight.Bold)
        for (i in 1..4) {
            ActivityItem()
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
fun DrawerContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFFD9D9D9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
     {
        MenuItems(R.drawable.calendar_month, R.string.pagina_agenda,
            novaPagina = Intent(
            LocalContext.current, AgendaActivity::class.java
            )
        )
        MenuItems(R.drawable.account_circle, R.string.menu_lateral_perfil,
            novaPagina = Intent(
                LocalContext.current, PerfilActivity::class.java
            )
        )
        MenuItems(R.drawable.content_cut, R.string.card_servicos,
            novaPagina = Intent(
                LocalContext.current, ServicosActivity::class.java
            )
        )
        MenuItems(R.drawable.orders, R.string.todos_os_pedidos,
            novaPagina = Intent(
                LocalContext.current, ServicosActivity::class.java
            )
        )
        MenuItems(R.drawable.star, R.string.barra_navegacao_avaliacoes,
            novaPagina = Intent(
                LocalContext.current, AgendaActivity::class.java
            )
        )
        MenuItems(R.drawable.move_item, R.string.sair,
            novaPagina = Intent(
                LocalContext.current, MainActivity::class.java
            )
        )
    }
}

@Composable
fun MenuItems(icon: Int, page: Int, novaPagina: Intent) {
    val context = LocalContext.current
    var color by remember { mutableStateOf(Color.Black) }
    if (icon == R.drawable.move_item) {
        color = Color.Red
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                context.startActivity(novaPagina)
            })
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


@Composable
fun BottomNavigationBar() {
    var selectedIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text(stringResource(R.string.barra_navegacao_home)) },
            selected = selectedIndex == 0,
            onClick = {
                selectedIndex = 0
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.border_color),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text(stringResource(R.string.barra_navegacao_pedidos)) },
            selected = selectedIndex == 1,
            onClick = {
                selectedIndex = 1
                val intent = Intent(context, ServicosActivity::class.java)
                context.startActivity(intent)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text(stringResource(R.string.barra_navegacao_avaliacoes)) },
            selected = selectedIndex == 2,
            onClick = { selectedIndex = 2 }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MaterialThemePreview() {
    HomeScreen()
}
