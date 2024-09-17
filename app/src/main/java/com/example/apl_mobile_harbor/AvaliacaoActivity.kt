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
import androidx.compose.material3.Text
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
import com.example.apl_mobile_harbor.ui.theme.AplmobileharborTheme

class AvaliacaoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplmobileharborTheme {
                }
            }
        }
    }

@Composable
fun AvaliacaoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarAvaliacoes()
        AvaliacaoInfo()
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar()
    }
}

@Composable
fun TopBarAvaliacoes() {
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
            text = "Minhas avaliações",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun AvaliacaoInfo() {
    Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp)) {
        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "Alex Batista",
            time = "09:00 - 09:30"
        )
        Spacer(modifier = Modifier.height(8.dp))

        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "Nunes Filho",
            time = "10:00 - 10:30"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "Ronaldo Reis",
            time = "13:30 - 14:00"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AvaliacaoItem(
            imageRes = R.drawable.user,
            name = "João Carlos",
            time = "15:30 - 16:00"
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AvaliacaoItem(
    imageRes: Int,
    name: String,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
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
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0E28AC)
                )
                Text(text = time, fontSize = 14.sp, color = Color.Gray)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Image(
                            painter = painterResource(id = R.drawable.estrela),
                            contentDescription = "Estrela de avaliação",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "5.0",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AvaliacaoPreview() {
    AvaliacaoScreen()
}
