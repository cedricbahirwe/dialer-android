package com.cedricbahirwe.dialer.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
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
//                    QuickDialingView()
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun QuickDialingView(navController: NavHostController) {
    val gradientColors = listOf(Color.Red, Color.Blue)
    var composedCode by remember { mutableStateOf("") }
    fun dialCode() {
        println("0782628511")
    }

    fun codeChanged(newKey: String) {
        println("New Key: $newKey")
        if (newKey == "X" && composedCode.isNotEmpty()) {
            composedCode = composedCode.dropLast(1)
        } else {
            composedCode += newKey
        }
    }
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
                text = composedCode,
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
                isFullMode = false,
                showDeleteBtn = false,
                btnSize = 70f,
                btnColors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Gray.copy(0.4f),
                    contentColor = Color.White
                ),
                onEditChanged = { codeChanged(it) }
            )

            Spacer(Modifier.padding(vertical = 20.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 40.dp)
            ) {
                Row(Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterVertically),
                        shape = CircleShape,
                        border = null,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MainRed, contentColor = Color.White)
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Go Back icon",
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    if (composedCode.isNotEmpty()) {
                        OutlinedButton(
                            onClick = {
                                codeChanged("X")
                            },
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterVertically),
                            shape = CircleShape,
                            border = null,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MainRed, contentColor = Color.White)

                        ) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = "Go Back icon",
                            )
                        }
                    }

                }

                OutlinedButton(
                    onClick = { dialCode() },
                    modifier = Modifier.size(55.dp),
                    shape = CircleShape,
                    border = null,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = AccentBlue, contentColor = Color.White),
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
        val navController = rememberNavController()
        QuickDialingView(navController = navController)
    }
}