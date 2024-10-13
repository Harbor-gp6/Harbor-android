package com.example.apl_mobile_harbor.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apl_mobile_harbor.R

@Composable
fun AvaliacaoScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(stringResource(R.string.label_minhas_avaliacoes), navController)
        AvaliacaoInfo()
        Spacer(modifier = Modifier.weight(1f))
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