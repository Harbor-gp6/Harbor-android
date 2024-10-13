package com.example.apl_mobile_harbor.activities.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.componentes.AgendaScreen
import com.example.apl_mobile_harbor.componentes.AvaliacaoScreen
import com.example.apl_mobile_harbor.componentes.DrawerContent
import com.example.apl_mobile_harbor.componentes.Header
import com.example.apl_mobile_harbor.componentes.HomeScreen
import com.example.apl_mobile_harbor.componentes.PedidoScreen
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Navegacao(rememberNavController())
            }
        }
    }
}

@Composable
fun Navegacao(
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                NavHost(
                    navController = navController,
                    startDestination = "homeScreen"
                ) {
                    composable("homeScreen") {
                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            drawerContent = {
                                DrawerContent(modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
                                    .width(300.dp),
                                    navController
                                )
                            }
                        ) {
                            Scaffold(
                                topBar = {
                                    Header(
                                        onMenuClick = { scope.launch { drawerState.open() } },
                                        message = stringResource(R.string.saudacao, "Tiago"),
                                        isHomeActivity = true
                                    )
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White)
                                        .padding(it)
                                ) {
                                    HomeScreen()
                                }
                            }
                        }
                    }
                    composable("pedidosScreen") {
                        PedidoScreen(navController)
                    }
                    composable("avaliacoesScreen") {
                        AvaliacaoScreen(navController)
                    }
                    composable("agendaScreen") {
                        AgendaScreen(navController)
                    }
                }
            }
            BottomNavigationBar(navController = navController)
        }
    }
}



@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
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
            label = { Text(stringResource(R.string.barra_navegacao_home)) },
            selected = selectedIndex == 0,
            onClick = {
                selectedIndex = 0
                navController.navigate("homeScreen")
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
                navController.navigate("pedidosScreen")
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
            onClick = {
                selectedIndex = 2
                navController.navigate("avaliacoesScreen")
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MaterialThemePreview() {
    Navegacao(rememberNavController())
}
