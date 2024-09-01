package com.example.apl_mobile_harbor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.apl_mobile_harbor.ui.theme.AplmobileharborTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplmobileharborTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    var showMainContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000) // 3 segundos de atraso
        showMainContent = true
    }

    if (showMainContent) {
        MainContent()
    } else {
        SplashScreenContent()
    }
}

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E28AC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.harborlogobranco),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
        )
    }
}

@Composable
fun MainContent() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)) // Cor de fundo
    ) { innerPadding ->
        Tela(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Tela(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.harborlogoazul),
            contentDescription = "Fofinho",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.size(40.dp))

        var email by remember { mutableStateOf("") }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("exemplo@gmail.com") }
        )

        Spacer(modifier = Modifier.size(20.dp))

        var senha by remember { mutableStateOf("") }
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            supportingText = {
                Text("Esqueceu a senha?")
            },
            placeholder = { Text("exemplo@gmail.com") }
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(top = 20.dp)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC) // Use containerColor para a cor de fundo
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text("Entrar", color = Color.White) // Opcional: mudar a cor do texto para garantir contraste
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AplmobileharborTheme {
        MainContent()
    }
}
