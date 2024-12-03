package com.example.apl_mobile_harbor.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.view_models.prestador.PrestadorViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditarPerfil(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Meu perfil", navController, true)
        ProfileEditSection()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ProfileEditSection(prestadorViewModel: PrestadorViewModel = koinViewModel()) {
    val usuario by prestadorViewModel.prestadorAtual.observeAsState()
    var nome by remember(usuario?.nome) { mutableStateOf(usuario?.nome ?: "") }
    var sobrenome by remember(usuario?.sobrenome) { mutableStateOf(usuario?.sobrenome ?: "") }
    var telefone by remember(usuario?.telefone) { mutableStateOf(usuario?.telefone ?: "") }
    var email by remember(usuario?.email) { mutableStateOf(usuario?.email ?: "") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = { Text(stringResource(R.string.label_nome)) },
            value = nome,
            onValueChange = {
                nome = it
                prestadorViewModel.atualizarNome(it)
            }
        )
        OutlinedTextField(
            label = { Text(stringResource(R.string.label_sobrenome)) },
            value = sobrenome,
            onValueChange = {
                sobrenome = it
                prestadorViewModel.atualizarSobrenome(it)
            }
        )
        OutlinedTextField(
            label = { Text(stringResource(R.string.label_telefone)) },
            value = telefone,
            onValueChange = {
                telefone = it
                prestadorViewModel.atualizarTelefone(it)
            }
        )
        OutlinedTextField(
            label = { Text(stringResource(R.string.label_email)) },
            value = email,
            onValueChange = {
                email = it
                prestadorViewModel.atualizarEmail(it)
            }
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(
            onClick = {

            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC)
            )
        ) {  Text(stringResource(R.string.botao_cancelar)) }
        Button(
            onClick = {

            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC)
            )
        ) { Text(stringResource(R.string.botao_salvar)) }
    }
}