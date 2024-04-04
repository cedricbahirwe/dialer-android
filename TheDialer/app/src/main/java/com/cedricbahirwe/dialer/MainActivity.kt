package com.cedricbahirwe.dialer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.navigation.NavGraph
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import com.cedricbahirwe.dialer.viewmodel.isPermissionGranted
import com.cedricbahirwe.dialer.viewmodel.permissions
import com.cedricbahirwe.dialer.viewmodel.requestPermission

private const val REQUEST_CONTACT_PICKER = 1

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        initializeViewModel()

        handlePermissions()

        setContent {
            MainScreen(
                mainViewModel = mainViewModel
            )
        }
    }

    private fun initializeViewModel() {
        mainViewModel = MainViewModel(
            context = this.applicationContext, AppSettingsRepository.getInstance(
                this.applicationContext
            )
        )
    }

    private fun handlePermissions() {
        if (!isPermissionGranted(this, permissions[0])) {
            Toast.makeText(
                this,
                getString(R.string.toast_ussd_permission_is_not_granted),
                Toast.LENGTH_SHORT
            ).show()
            requestPermission(this, permissions[0])
        }
    }

    // on below line we are calling on activity result method.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // on below line we are checking if result code is ok or not.
        if (resultCode != Activity.RESULT_OK) return

        // on below line we are checking if data is not null.
        if (requestCode === REQUEST_CONTACT_PICKER && data != null) {
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
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    DialerTheme {
        val navController = rememberNavController()
        NavGraph(navController, mainViewModel, openContactList = {
            if (hasContactPermission(activity)) {
                // if permission granted open intent to pick contact/
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type =
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                ActivityCompat.startActivityForResult(
                    activity,
                    intent,
                    REQUEST_CONTACT_PICKER,
                    null
                )
            } else {
                // if permission not granted requesting permission .
                requestContactPermission(context, activity)
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val mainViewModel = MainViewModel(
        context = LocalContext.current.applicationContext, AppSettingsRepository.getInstance(
            LocalContext.current.applicationContext
        )
    )
    DialerTheme {
        MainScreen(mainViewModel = mainViewModel)
    }
}

// Function to open the contact picker and handle the result
fun hasContactPermission(context: Context): Boolean {
    // on below line checking if permission is present or not.
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) ==
            PackageManager.PERMISSION_GRANTED;
}

fun requestContactPermission(context: Context, activity: Activity) {
    // on below line if permission is not granted requesting permissions.
    if (!hasContactPermission(context)) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_CONTACTS), 1)
    }
}
