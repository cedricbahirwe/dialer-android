package com.cedricbahirwe.dialer

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.navigation.NavGraph
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import com.cedricbahirwe.dialer.viewmodel.isPermissionGranted
import com.cedricbahirwe.dialer.viewmodel.permissions
import com.cedricbahirwe.dialer.viewmodel.requestPermission

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()

        mainViewModel = MainViewModel(
            context = this.applicationContext, AppSettingsRepository.getInstance(
                this.applicationContext
            )
        )
        if (!isPermissionGranted(this, permissions[0])) {
            Toast.makeText(
                this,
                getString(R.string.toast_ussd_permission_is_not_granted),
                Toast.LENGTH_SHORT
            ).show()
            requestPermission(this, permissions[0])
        }
        setContent {
            MainScreen(
                mainViewModel = mainViewModel
            )
        }
    }




    // on below line we are calling on activity result method.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // on below line we are checking if result code is ok or not.
        if (resultCode != Activity.RESULT_OK) return

        // on below line we are checking if data is not null.
        if (requestCode == 1 && data != null) {
            // on below line we are getting contact data
            val contactData: Uri? = data.data

            // on below line we are creating a cursor
            val cursor: Cursor = managedQuery(contactData, null, null, null, null)

            // on below line we are moving cursor.
            cursor.moveToFirst()

            // on below line we are getting our
            // number and name from cursor
            val number: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val name: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

            // on the below line we are setting values.
            mainViewModel.setContactName(name)
            mainViewModel.setContactNumber(number)
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    DialerTheme {
        val navController = rememberNavController()
        NavGraph(navController, mainViewModel)
    }
}
