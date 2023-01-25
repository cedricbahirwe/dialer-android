package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.ui.theme.MainRed

class QuickDialingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    QuickDialingView()
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun QuickDialingView() {
    val gradientColors = listOf(Color.Red, Color.Blue)

    Surface(contentColor = Color.White) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.dialer_applogo),
                modifier = Modifier
                    .size(80.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                contentDescription = "Dialer Logo",
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "*182#",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    ),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.weight(1f))

            PinView(
                "*182#", btnColors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Gray.copy(0.2f), contentColor = Color.White
                )
            )

            Spacer(Modifier.padding(vertical = 20.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 40.dp)
            ) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterStart),
                    shape = CircleShape,
                    border = null,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MainRed)
                ) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Go Back icon",
                    )
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.size(55.dp),
                    shape = CircleShape,
                    border = null,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Green),
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Dial icon",
                        Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickDialingPreview() {
    DialerTheme {
        QuickDialingView()
    }
}
