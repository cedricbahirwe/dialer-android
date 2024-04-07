package com.cedricbahirwe.dialer.screens

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.cedricbahirwe.dialer.viewmodel.PhoneDialer
import kotlinx.coroutines.delay

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

@Composable
fun QuickDialingView(navController: NavHostController) {
    val context = LocalContext.current

    var showInValidMsg by remember { mutableStateOf(false) }
    var composedCode by remember { mutableStateOf("") }

    fun handleNewPinInput(newKey: String) {
        if (newKey == "X" && composedCode.isNotEmpty()) {
            composedCode = composedCode.dropLast(1)
        } else {
            composedCode += newKey
        }
    }

    fun manageInvalidCode() {
        showInValidMsg = true
    }

    fun performQuickDial(context: Context, ussd: String)  {
        if (ussd.contains("*") && ussd.contains("#") && ussd.length >= 5) {
            PhoneDialer.getInstance(context)
                .dial(ussd) {
                    when (it) {
                        true -> Unit//Toast.makeText(context, "USSD dialled successfully", Toast.LENGTH_SHORT).show()
                        false -> Toast.makeText(context, "Sorry, Unable to perform the operation", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            manageInvalidCode()
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

            if (showInValidMsg) {
                LaunchedEffect(true) {
                    delay(2000) // 2000 milliseconds delay
                    showInValidMsg = false
                }
            }

            Text(
                "Invalid code. Check it and try again.",
                color = Color.Red,
                modifier = Modifier.alpha(if (showInValidMsg) 1f else 0f)
            )

            Text(
                text = composedCode,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Red, Color.Blue)
                    ),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.weight(1f))

            PinView(
                isFullMode = true,
                showDeleteBtn = false,
                btnSize = 70f,
                btnColors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Gray.copy(0.4f),
                    contentColor = Color.White
                ),
                onEditChanged = { handleNewPinInput(it) }
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
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go Back icon",
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    if (composedCode.isNotEmpty()) {
                        OutlinedButton(
                            onClick = {
                                handleNewPinInput("X")
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
                    onClick = { performQuickDial(context, composedCode) },
                    modifier = Modifier.size(65.dp),
                    shape = CircleShape,
                    border = null,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = AccentBlue, contentColor = Color.White),
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Dial icon",
                        Modifier.size(40.dp)
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