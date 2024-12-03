package com.example.apl_mobile_harbor.componentes

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R
import com.example.apl_mobile_harbor.view_models.avaliacao.AvaliacaoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Meu perfil", navController, true)
        ProfileImageSection()
        ContactInfoSection(navController)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ProfileImageSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.tiago),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tiago Romano",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row ( horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Text(text = "30 anos", fontSize = 14.sp, color = Color.Black)
            Text(text = "-", fontSize = 14.sp, color = Color.Black)
            Text(text = "Masculino", fontSize = 14.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(30.dp))
        RatingSection()
    }
}

@Composable
fun RatingSection(
    avaliacaoViewModel: AvaliacaoViewModel = koinViewModel()
) {

    var bool by remember{ mutableStateOf(true) }

    LaunchedEffect(key1 = bool) {
        avaliacaoViewModel.getMedia()
        avaliacaoViewModel.getAvaliacoes()
        bool = false
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = avaliacaoViewModel.mediaPrestador.value.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.estrela_cheia),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(120.dp))
        Text(text = "${avaliacaoViewModel.avaliacoesPrestador.size} avaliações", fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun ContactInfoSection(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(end = 50.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = {
                navController.navigate("editPerfilScreen")
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .width(90.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0E28AC)
            ),
            shape = RoundedCornerShape(20)
        ) {
            Text("Editar")
        }
    }
    Row {
        Column(modifier = Modifier.padding(16.dp)) {
            ContactItem(imageRes = R.drawable.telefone, info = "(82) 9999-9999")
            ContactItem(imageRes = R.drawable.email, info = "barbeiro@gmail.com")
            ContactItem(
                imageRes = R.drawable.endereco,
                info = "Rua Bipirópa, 200, apto 651, Ponta do Canto, Piauí - RC"
            )
        }
    }
}

@Composable
fun ContactItem(imageRes: Int, info: String) {
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
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = info, fontSize = 16.sp, color = Color.Black)
    }
}