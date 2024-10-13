package com.example.apl_mobile_harbor.componentes

import android.content.Intent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apl_mobile_harbor.BaixaNegativaActivity
import com.example.apl_mobile_harbor.BaixaPositivaActivity
import com.example.apl_mobile_harbor.EditarServico
import com.example.apl_mobile_harbor.R

@Composable
fun ServiceDetailScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.seta_esquerda),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                        .clickable(onClick = {

                        })
                )
                Text(
                    text = "José Alves",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(24.dp))
            }
            Text(text = "Terça, 10 de Set 24 | 10:00", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botões de ação
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton(
                icon = R.drawable.check,
                text = stringResource(R.string.dar_baixa),
                onClick = {
                    val intent = Intent(context, BaixaPositivaActivity::class.java)
                    context.startActivity(intent)
                }
            )
            ActionButton(
                icon = R.drawable.cancel,
                text = stringResource(R.string.cancelar),
                onClick = {
                    val intent = Intent(context, BaixaNegativaActivity::class.java)
                    context.startActivity(intent)
                }
            )
            ActionButton(
                icon = R.drawable.edit,
                text = stringResource(R.string.editar),
                onClick = {
                    val intent = Intent(context, EditarServico::class.java)
                    context.startActivity(intent)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.detalhes_cliente),
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(label = stringResource(R.string.label_nome_completo) + ":", info = "José Alves")
            InfoRow(label = stringResource(R.string.label_email) + ":", info = "jose.alves@gmail.com")
            InfoRow(label = stringResource(R.string.label_telefone) + ":", info = "(82) 9999-9999")
            InfoRow(label = stringResource(R.string.label_cpf) + ":", info = "423.713.483-85")
        }
        Spacer(modifier = Modifier.weight(1f))
//        BottomNavigationBar()
    }
}


@Composable
fun ActionButton(icon: Int, text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .size(width = 100.dp, height = 100.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            color = Color.DarkGray,
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun InfoRow(label: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(150.dp))
        Text(
            modifier = Modifier,
            text = info,
            color = Color.DarkGray,
            textAlign = TextAlign.Right
        )
    }
}