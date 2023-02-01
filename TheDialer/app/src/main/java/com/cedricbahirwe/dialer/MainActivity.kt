package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.navigation.NavGraph
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
@Composable
fun Greeting(name: String) {
    Surface(color = Color.Magenta) {
        Text(text = "Hi, my name is $name!", modifier = Modifier.padding(24.dp))
    }
}
@Composable
fun MainScreen() {
    DialerTheme {
        val navController = rememberNavController()
        NavGraph(navController)
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DialerTheme {
        Greeting("Cedric")
    }
}