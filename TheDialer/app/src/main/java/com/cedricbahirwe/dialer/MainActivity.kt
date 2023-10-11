package com.cedricbahirwe.dialer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.navigation.NavGraph
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.isPermissionGranted
import com.cedricbahirwe.dialer.viewmodel.permissions
import com.cedricbahirwe.dialer.viewmodel.requestPermission

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        if (!isPermissionGranted(this, permissions[0])) {
            Toast.makeText(
                this,
                getString(R.string.toast_ussd_permission_is_not_granted),
                Toast.LENGTH_SHORT
            ).show()
            requestPermission(this, permissions[0])
        }
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
