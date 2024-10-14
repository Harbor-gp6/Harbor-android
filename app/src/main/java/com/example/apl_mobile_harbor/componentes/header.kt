package com.example.apl_mobile_harbor.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apl_mobile_harbor.R

@Composable
fun Header(onMenuClick: () -> Unit, message: String, isHomeActivity: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isHomeActivity) {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Text(
            text = message,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A65DF)
        )
        Icon(
            painter = painterResource(id = R.drawable.tiago),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}


