package com.example.apl_mobile_harbor.componentes

import android.app.Activity
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.activities.perfil.PerfilActivity
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.activities.app.AppActivity
import com.example.apl_mobile_harbor.activities.login.LoginActivity
import com.example.apl_mobile_harbor.classes.atividade.formatDateToActivity
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.pedido.formatDate
import com.example.apl_mobile_harbor.view_models.atividade.AtividadeViewModel
import com.example.apl_mobile_harbor.view_models.login.LoginViewModel
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Composable
fun HomeScreen(navController: NavHostController) {
    val tokenManager = TokenManager(LocalContext.current, null)
    val usuario = tokenManager.getUsuario()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
                    .width(300.dp),
                navController = navController
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (usuario != null) {
                    Header(
                        onMenuClick = { scope.launch { drawerState.open() } },
                        message = stringResource(R.string.saudacao, usuario.nome),
                        isHomeActivity = true
                    )
                }

            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues)
                ) {
                    MenuGrid(navController = navController)
                    RecentActivities()
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        )
    }
}

@Composable
fun MenuGrid(navController: NavHostController) {
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
                title = R.string.card_agenda,
                iconRes = R.drawable.calendar_month,
                modifier = Modifier.weight(1f),
                weigthLeft = 0.8f,
                weigthRight = 0.2f,
                handleClick = {
                    navController.navigate("agendaScreen") // Navegação correta
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuCard(
                title = R.string.card_servicos,
                iconRes = R.drawable.pedidos,
                modifier = Modifier.weight(1f),
                weigthLeft = 0.6f,
                weigthRight = 0.4f,
                handleClick = {
                    navController.navigate("pedidosScreen")
                }
            )
            MenuCard(
                title = R.string.card_perfil,
                iconRes = R.drawable.person,
                modifier = Modifier.weight(1f),
                weigthLeft = 0.6f,
                weigthRight = 0.4f,
                handleClick = {
                    val intent = Intent(context, PerfilActivity::class.java)
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
        )
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
fun RecentActivities(
    atividadeViewModel: AtividadeViewModel = koinViewModel()
    ) {
    var contador by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.atividade_recente), fontWeight = FontWeight.Bold)
        LazyColumn {
            items(items = atividadeViewModel.atividadesRecentes) { atividade ->
                atividade.pedido.let {
                    if (it != null) {
                            ActivityItem(
                                atividade.nomeCliente,
                                atividade.servico,
                                formatDateToActivity(it.dataAgendamento),
                                formatDate(it.dataAgendamento),
                                formatDate(atividade.dataCriacao)
                            )
                            contador++
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityItem(
    nomeCliente: String,
    servico: String,
    dataAgendamento: String,
    horaAgendamento: String,
    dataCriacao: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD9D9D9) // Azul para o card
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.aberto),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = stringResource(R.string.pedido_agendado_usuario, nomeCliente),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(
                        R.string.data_hora_pedido,
                        servico,
                        dataAgendamento, horaAgendamento
                    ),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .background(Color(0xFFD9D9D9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuItems(
            R.drawable.calendar_drawer,
            R.string.pagina_agenda,
            onClick = { navController.navigate("agendaScreen") }
        )
        MenuItems(
            R.drawable.account_circle,
            R.string.menu_lateral_perfil,
            onClick = {
                val intent = Intent(context, PerfilActivity::class.java)
                context.startActivity(intent)
            }
        )
        MenuItems(
            R.drawable.content_cut,
            R.string.card_servicos,
            onClick = { navController.navigate("pedidosScreen") }
        )
        MenuItems(
            R.drawable.star,
            R.string.barra_navegacao_avaliacoes,
            onClick = { navController.navigate("avaliacoesScreen") }
        )
        MenuItems(
            R.drawable.move_item,
            R.string.sair,
            onClick = {
                loginViewModel.logout()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            }
        )
    }
}

@Composable
fun MenuItems(icon: Int, page: Int, onClick: () -> Unit) {
    var color by remember { mutableStateOf(Color.Black) }
    if (icon == R.drawable.move_item) {
        color = Color.Red
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFD9D9D9))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = page),
                fontSize = 20.sp,
                color = color
            )
        }
    }
}
