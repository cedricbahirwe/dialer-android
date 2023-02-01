package com.cedricbahirwe.dialer.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    Button(
        onClick = onClick,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        Text(text, fontSize = 16.sp)
    }
}