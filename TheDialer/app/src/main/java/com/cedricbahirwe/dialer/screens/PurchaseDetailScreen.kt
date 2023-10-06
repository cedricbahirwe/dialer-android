package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PurchaseDetailView() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Column {
                val fieldBorderGradient = remember {
                    Brush.linearGradient(
                        colors = listOf(Color.Green, Color.Blue)
                    )
                }

                val fieldModifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .border(
                        BorderStroke(1.dp, fieldBorderGradient),
                        RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.primary.copy(0.06f))
                    .wrapContentHeight(Alignment.CenterVertically)
                Text(
                    "Enter Amount",
                    fontWeight = FontWeight.SemiBold,
                    modifier = fieldModifier,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Spacer(Modifier.padding(vertical = 10.dp))

                Text(
                    "Enter Pin",
                    fontWeight = FontWeight.SemiBold,
                    modifier = fieldModifier,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }

            val btnElevation = ButtonDefaults.elevation(
                defaultElevation = 20.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 15.dp,
                focusedElevation = 10.dp
            )

            Spacer(Modifier.padding(14.dp))

            Button(
                onClick = {},
                Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.background
                ),
                elevation = btnElevation,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Confirm", Modifier.padding(start = 1.dp))
            }

            Spacer(Modifier.padding(8.dp))

            PinView {}
        }
    }
}