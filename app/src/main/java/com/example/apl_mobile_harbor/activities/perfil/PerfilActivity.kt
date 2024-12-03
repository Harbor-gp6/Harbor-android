package com.example.apl_mobile_harbor.activities.perfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.componentes.EditarPerfil
import com.example.apl_mobile_harbor.componentes.ProfileScreen
import com.example.apl_mobile_harbor.componentes.TopBar
import com.example.apl_mobile_harbor.view_models.avaliacao.AvaliacaoViewModel
import com.example.apl_mobile_harbor.view_models.prestador.PrestadorViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NavegacaoPerfil(rememberNavController())
            }
        }
    }
}

@Composable
fun NavegacaoPerfil(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "viewPerfilScreen",
    ) {
        composable("viewPerfilScreen") {
            ProfileScreen(navController)
        }
        composable("editPerfilScreen") {
            EditarPerfil(navController)
        }
    }
}


