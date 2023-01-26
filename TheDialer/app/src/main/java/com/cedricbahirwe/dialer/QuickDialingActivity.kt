package com.cedricbahirwe.dialer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.Composable
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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
fun QuickDialingView(phoneDialer: PhoneDialer) {
    val gradientColors = listOf(Color.Red, Color.Blue)
    fun dialCode() {
    println("It is ${phoneDialer == null}")
        phoneDialer.dial("0782628511")
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
                    onClick = { dialCode() },
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

//@Preview(showBackground = true)
//@Composable
//fun QuickDialingPreview() {
//    DialerTheme {
//        QuickDialingView()
//    }
//}

class PhoneDialer(private val context: Context) {
    private val CALL_PHONE_PERMISSION_REQUEST_CODE = 1

    fun dial(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_REQUEST_CODE)
        } else {
            makeCall(phoneNumber)
        }
    }

    private fun makeCall(phoneNumber: String) {
        val dial = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        if (dial.resolveActivity(context.packageManager) != null) {
            context.startActivity(dial)
        } else {
            // no app to handle phone calls
            Toast.makeText(context, "No app found to handle phone calls", Toast.LENGTH_SHORT).show()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            CALL_PHONE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall("0782628511")
                } else {
                    // permission denied
                    Toast.makeText(context, "Permission to make phone calls denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
