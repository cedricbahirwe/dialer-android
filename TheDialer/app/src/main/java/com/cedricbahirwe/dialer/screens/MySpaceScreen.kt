package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.ui.theme.MainRed

@Composable
fun MySpaceScreen(
    onDismissed: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fieldBorderGradient = Brush.linearGradient(
            colors = listOf(MainRed, AccentBlue)
        )

        Text(
            stringResource(R.string.coming_soon),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .background(
                    brush = fieldBorderGradient,
                    shape = RoundedCornerShape(25.dp)

                )
                .padding(8.dp)
        )


        Text(
            "My Space",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            "A unified space for buying electricity, Voice packs, Internet and more.",
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.alpha(0.8f),
        )

        Button(
            onClick = {
                onDismissed()
            },

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(
                    brush = fieldBorderGradient,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text("I Can't wait!")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MySpaceScreenPreview() {
    DialerTheme {
        MySpaceScreen {}
    }
}