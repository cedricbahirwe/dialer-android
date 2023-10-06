package com.cedricbahirwe.dialer.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleView(title: String, modifier: Modifier = Modifier) {
    Row {
        Text(
            title,
            modifier = modifier.padding(vertical = 15.dp),
            color = MaterialTheme.colors.primary,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}