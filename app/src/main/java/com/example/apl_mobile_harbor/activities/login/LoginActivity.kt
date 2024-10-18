package com.example.apl_mobile_harbor.activities.login

import com.example.apl_mobile_harbor.view_models.login.LoginViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apl_mobile_harbor.activities.app.AppActivity
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.auth.TokenProvider
import com.example.apl_mobile_harbor.modulos.appModule
import com.example.apl_mobile_harbor.ui.theme.AplmobileharborTheme
import kotlinx.coroutines.delay
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class LoginActivity : ComponentActivity() {
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@LoginActivity)
            modules(appModule)
        }

        tokenManager = TokenManager(this)

        if (tokenManager.isUserLoggedIn()) {
            TokenProvider.token = tokenManager.getTokenFromPrefs()
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            return
        }
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
            .background(Color(0xFFF2F2F2))
    ) { innerPadding ->
        Tela(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Tela(modifier: Modifier = Modifier, loginViewModel: LoginViewModel = koinViewModel()) {
    val context = LocalContext.current
    val loginResponse by loginViewModel.loginResponse.observeAsState()

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


        OutlinedTextField(
            value = loginViewModel.email,
            onValueChange = { loginViewModel.email = it },
            label = { Text(stringResource(R.string.label_email)) },
            placeholder = { Text(stringResource(R.string.exemplo_email)) }
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = loginViewModel.senha,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { loginViewModel.senha = it },
            label = { Text(stringResource(R.string.label_senha)) },
            supportingText = {
                Text(
                    modifier = Modifier.clickable(onClick = {}),
                    text = stringResource(R.string.esqueceu_senha))
            },
            placeholder = { Text(stringResource(R.string.exemplo_senha)) }
        )

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = loginViewModel.manterConectado,
                    onCheckedChange = { loginViewModel.manterConectado = it }
                )
                Text(stringResource(R.string.manter_conectado))
            }
        }

        Button(
            onClick = {
                loginViewModel.login()
                if (TokenProvider.token != null) {
                    val intent = Intent(context, AppActivity::class.java)
                    context.startActivity(intent)
                }
            },
            enabled = !loginViewModel.isEmAndamento,
            modifier = Modifier
                .padding(top = 20.dp)
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC)
            ),
            shape = RoundedCornerShape(50)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (!loginViewModel.isEmAndamento) {
                    Text(
                        text = stringResource(R.string.botao_entrar),
                        color = Color.White
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color(0xFF0E28AC)
                    )
                }
            }
        }
    }
    loginResponse?.let { response ->
        LaunchedEffect(response) {
            context.startActivity(Intent(context, AppActivity::class.java))
        }
    }
    ModalErro(loginViewModel)
}

@Composable
fun ModalErro(loginViewModel: LoginViewModel = viewModel()) {
    var texto: String = ""
    if (loginViewModel.emailOuSenhaEmBranco) {
        texto = "⚠\uFE0F Email ou senha inválidos"
    }
    if (loginViewModel.erroApi) {
        texto = "⚠\uFE0F Ops! Alguma coisa deu errado. \r\nTente novamente"
    }
    if (loginViewModel.emailOuSenhaEmBranco || loginViewModel.erroApi) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(Color(228, 3, 80, 210))
                .zIndex(1f)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = texto,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
        LaunchedEffect("alerta-topo") {
            delay(5000)
            loginViewModel.erroApi = false
            loginViewModel.emailOuSenhaEmBranco = false
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
