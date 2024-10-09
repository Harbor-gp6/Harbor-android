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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Perfil", navController )
        ProfileImageSection()
        ContactInfoSection()
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TopBarPerfil() {
    val context = LocalContext.current
    val intent = Intent(context, HomeActivity::class.java)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.seta_esquerda),
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
                .clickable(onClick = { context.startActivity(intent) })
        )
        Text(
            text = "Meu perfil",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(24.dp))
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
fun RatingSection() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) {
            Image(
                painter = painterResource(id = R.drawable.estrela),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "5.0")
        Spacer(modifier = Modifier.width(120.dp))
        Text(text = "73 avaliações", fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun ContactInfoSection() {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        ContactItem(imageRes = R.drawable.fone, info = "(82) 9999-9999")
        ContactItem(imageRes = R.drawable.mail, info = "barbeiro@gmail.com")
        ContactItem(imageRes = R.drawable.loca, info = "Rua Bipirópa, 200, apto 651, Ponta do Canto, Piauí - RC")
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

@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
