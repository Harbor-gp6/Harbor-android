package com.example.apl_mobile_harbor.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apl_mobile_harbor.R

// Baixa positiva

@Composable
fun BaixaPositivaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        DetalheBaixaPositiva()
        Spacer(modifier = Modifier.height(60.dp))
        BotoesLateral2()
    }
}

@Composable
fun DetalheBaixaPositiva() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.label_confirmar_baixa),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 45.sp
        )

        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_cliente)  + " Alex Batista",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.label_servico) + " Corte de cabelo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.label_horario_conclusao) + " 09:31",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}

@Composable
fun BotoesLateral2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6D6767),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .height(150.dp)
        ) {
            Text(
                text = stringResource(R.string.botao_voltar),
                fontSize = 20.sp)
        }

        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .height(150.dp)
        ) {
            Text(
                text = stringResource(R.string.botao_dar_baixa),
                fontSize = 20.sp)
        }
    }
}

//Baixa negativa

@Composable
fun BaixaNegativaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        DetalheBaixaNegativa()
        Spacer(modifier = Modifier.height(100.dp))
        BotoesLateral()
    }
}

@Composable
fun DetalheBaixaNegativa() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.label_confirmar_cancelamento),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 45.sp
        )

        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_cliente) + ":"  + " Alex Batista",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.label_servico) + ":" + " Corte de cabelo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.label_horario_conclusao) + ":" + " 09:31",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}

@Composable
fun BotoesLateral() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6D6767),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .height(150.dp)
        ) {
            Text(
                text = stringResource(R.string.botao_voltar),
                fontSize = 20.sp)
        }

        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF44336),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .height(150.dp)
        ) {
            Text(
                text = stringResource(R.string.botao_cancelar),
                fontSize = 20.sp)
        }
    }
}