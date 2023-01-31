package com.cedricbahirwe.dialer.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PhoneDialer(private val context: Context) {
    private val CALL_PHONE_PERMISSION_REQUEST_CODE = 1

    fun dial(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_REQUEST_CODE
            )
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

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            CALL_PHONE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall("0782628511")
                } else {
                    // permission denied
                    Toast.makeText(
                        context,
                        "Permission to make phone calls denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }
}