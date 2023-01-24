package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

class AirtimePurchaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PurchaseDetail()
                }
            }
        }
    }
}

@Composable
fun PurchaseDetail() {
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

        PinView("*182#")
    }
}

@Preview(showBackground = true)
@Composable
fun AirtimePurchasePreview() {
    DialerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PurchaseDetail()
        }
    }
}