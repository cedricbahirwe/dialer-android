package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.navigation.NavGraph
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            MainScreen()
        }
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
        MainScreen()
    }
}