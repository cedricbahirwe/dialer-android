package com.cedricbahirwe.dialer.data

import com.google.gson.Gson

object DialerSerializer {

    inline fun <reified T> toJson(obj: T): String {
        return Gson().toJson(obj, T::class.java)
    }

    inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, T::class.java)
    }
}