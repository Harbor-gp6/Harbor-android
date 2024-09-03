package com.example.apl_mobile_harbor

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header()
        MenuGrid()
        RecentActivities()
        BottomNavigationBar()
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = "Bem-vindo\nJeremias",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A65DF) // Azul do título
        )
        Image(
            painter = painterResource(id = R.drawable.user), // Certifique-se de ter essa imagem no seu drawable
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun MenuGrid() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuCard("Agenda", R.drawable.calendar_month, Modifier.weight(1f),
                weigthLeft = 0.6f,
                weigthRight = 0.4f,
                handleClick = {/*TODO*/}
                )
            MenuCard("Meu perfil", R.drawable.finance_mode, Modifier.weight(1f),
                weigthLeft = 0.6f,
                weigthRight = 0.4f,
                handleClick = {/*TODO*/}
                )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuCard("Meus serviços", R.drawable.person_add, Modifier.weight(1f),
                weigthLeft = 0.8f,
                weigthRight = 0.2f,
                handleClick = {/*TODO*/}
                )
        }
    }
}

@Composable
fun MenuCard(
    title: String,
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
                            text = title,
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
        Text(text = "Atividade recente", fontWeight = FontWeight.Bold)
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
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White) // Placeholder para a imagem
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Marcos agendou pedido", color = Color.Black, fontWeight = FontWeight.Bold)
                Text(text = "Corte de cabelo para hoje às 17:00", color = Color.Black)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    var selectedIndex by remember { mutableStateOf(0) }

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
            label = { Text("Home") },
            selected = selectedIndex == 0,
            onClick = { selectedIndex = 0 }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.border_color),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text("Pedidos") },
            selected = selectedIndex == 1,
            onClick = { selectedIndex = 1 }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text("Avaliações") },
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
