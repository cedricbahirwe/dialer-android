package com.cedricbahirwe.dialer.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleButton(title: String, size: Float, btnColors: ButtonColors, action: () -> Unit) {
    OutlinedButton(
        onClick = action,
        modifier = Modifier.size(size.dp),
        shape = CircleShape,
        border = null,
        contentPadding = PaddingValues(0.dp),
        colors = btnColors
    ) {
        Text(
            text = title,
            Modifier.padding(start = 1.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
