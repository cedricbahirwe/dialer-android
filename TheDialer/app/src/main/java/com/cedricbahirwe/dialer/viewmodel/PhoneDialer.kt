package com.cedricbahirwe.dialer.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telecom.TelecomManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cedricbahirwe.dialer.telephony.SIMUtils

class PhoneDialer private constructor(thisContext: Context) {
    private val context = thisContext
    private val simUtils = SIMUtils.getInstance(context)

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: PhoneDialer? = null

        fun getInstance(context: Context): PhoneDialer {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = PhoneDialer(context)
                INSTANCE = instance
                instance
            }
        }
    }


    fun dial(ussd: String, completion: ((Boolean) -> Unit) = {}) {
        if (isPermissionsGranted(context = context)) {
            call(
                context = context,
                ussd = ussd,
                simSlotIndex = simUtils?.getMTNRwandaSlotIndex()
            )
            completion(true)
        } else {
            requestPermissions(activity = context as Activity, permissions = permissions)
            completion(false)
        }
    }
}
fun dial(context: Context, ussd: String) {
    val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(ussd)))
    dialIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(dialIntent)
}

// Calls the code number using the appropriate sim card
fun call(context: Context, ussd: String, simSlotIndex: Int?) {
    simSlotIndex?.let {
        context.startActivity(
            getActionCallIntent(
                context, Uri.parse("tel:${Uri.encode(ussd)}"),
                simSlotIndex
            )
        )
        Log.d("TelephonyUtils", "Dialing USSD : $ussd")
    } ?: dial(context, ussd)
}

@SuppressLint("MissingPermission")
private fun getActionCallIntent(context: Context, uri: Uri, simSlotIndex: Int): Intent {
    // https://stackoverflow.com/questions/25524476/make-call-using-a-specified-sim-in-a-dual-sim-device
    val simSlotName = arrayOf(
        "extra_asus_dial_use_dualsim",
        "com.android.phone.extra.slot",
        "slot",
        "simslot",
        "sim_slot",
        "subscription",
        "Subscription",
        "phone",
        "com.android.phone.DialingMode",
        "simSlot",
        "slot_id",
        "simId",
        "simnum",
        "phone_type",
        "slotId",
        "slotIdx"
    )
    val intent = Intent(Intent.ACTION_CALL, uri)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.putExtra("com.android.phone.force.slot", true)
    intent.putExtra("Cdma_Supp", true)
    for (slotIndex in simSlotName) intent.putExtra(slotIndex, simSlotIndex)
    val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
    if (isPermissionGranted(context, Manifest.permission.READ_PHONE_STATE)) {
        val phoneAccountHandleList = telecomManager.callCapablePhoneAccounts
        if (phoneAccountHandleList != null && phoneAccountHandleList.size > simSlotIndex) intent.putExtra(
            "android.telecom.extra.PHONE_ACCOUNT_HANDLE",
            phoneAccountHandleList[simSlotIndex]
        )
    } else {
        Toast.makeText(context, "Permission is not granted", Toast.LENGTH_LONG).show()
    }
    return intent
}

fun isPermissionsGranted(context: Context): Boolean {
    permissions.forEach { permission ->
        if (!isPermissionGranted(context = context, permission = permission)) {
            return false
        }
    }
    return true
}

fun isPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE)

fun requestPermission(activity: Activity, permission: String) {
    requestPermissions(activity, arrayOf(permission))
}

fun requestPermissions(activity: Activity, permissions: Array<String>) {
    ActivityCompat.requestPermissions(activity, permissions, 1000)
}
