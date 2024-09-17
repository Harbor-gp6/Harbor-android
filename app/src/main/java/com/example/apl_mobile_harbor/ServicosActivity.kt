package com.example.apl_mobile_harbor

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.apl_mobile_harbor.ui.theme.AplmobileharborTheme

class ServicosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplmobileharborTheme {
                ServiceScreen()
            }
        }
    }
}

@Composable
fun ServiceScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarServicos()
        ServiceInfo()
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar()
    }
}

@Composable
fun TopBarServicos() {
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
            text = stringResource(R.string.label_meus_servicos),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ServiceInfo() {
    Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)) {
        ContactItem(
            imageRes = R.drawable.user,
            name = "Alex Batista",
            time = "10:30 - 11:00",
            service = "Corte de cabelo"
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactItem(
            imageRes = R.drawable.user,
            name = "José Alves",
            time = "11:00 - 11:30",
            service = "Barba"
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactItem(
            imageRes = R.drawable.user,
            name = "Ben Mendes",
            time = "11:30 - 12:00",
            service = "Corte de cabelo"
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactItem(
            imageRes = R.drawable.user,
            name = "Hugo Pontes",
            time = "12:00 - 12:30",
            service = "Corte de cabelo"
        )
    }
}

@Composable
fun ContactItem(
    imageRes: Int,
    name: String,
    time: String,
    service: String
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = {
                val intent = Intent(context, DetalhesServico::class.java)
                context.startActivity(intent)
            })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0E28AC))
                Text(text = time, fontSize = 14.sp, color = Color.Gray)
                Text(text = stringResource(R.string.label_servico) + ":" + " $service", fontSize = 14.sp, color = Color.Black)
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            IconButton(onClick = {
                val intent = Intent(context, BaixaPositivaActivity::class.java)
                context.startActivity(intent)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.aceitar),
                    contentDescription = "Confirmar",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
            }
            IconButton(onClick = {
                val intent = Intent(context, BaixaNegativaActivity::class.java)
                context.startActivity(intent)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.cancelar),
                    contentDescription = "Negar",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ServicosPreview() {
        ServiceScreen()
}