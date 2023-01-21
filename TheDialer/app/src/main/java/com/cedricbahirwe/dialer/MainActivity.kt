package com.cedricbahirwe.dialer

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DashBoardItem("Android")
                }
            }
        }
    }
}

@Composable
fun DashBoardItem(name: String) {
    Surface(color = MaterialTheme.colors.background) {
        Text(text = "$name",
        modifier = Modifier.padding(16.dp),
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DialerTheme {
        DashBoardItem("Buy airtime")
    }
}