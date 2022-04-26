package com.example.myapplication.helper.ktx

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import com.example.myapplication.ui.posts.PostsFragment

/**
 * Created by hosamalmowaqee on 7/4/21.
 * Copyright © 2021 TryCar. All rights reserved.
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

//fun LanguageSetting.getLanguageSafety(context: Context): String =
//    if (this.getLanguage(context) != null) this.getLanguage(context)!!.language
//    else "en"

fun HashMap<String, String>.getTextSafety(key: String): String =
    if (this[key] != null) this[key]!!
    else ""

fun Context.showToast(msg: Int, toastLength: Int = Toast.LENGTH_SHORT) {
    showToast(getString(msg), toastLength)
}

fun Context.showToast(msg: String, toastLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, toastLength).show()
}

fun Activity.getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun Activity.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun replaceNonstandardDigits(input: String?): String? {
    if (input == null || input.isEmpty()) {
        return input
    }
    val builder = StringBuilder()
    for (element in input) {
        if (isNonstandardDigit(element)) {
            val numericValue = Character.getNumericValue(element)
            if (numericValue >= 0) {
                builder.append(numericValue)
            }
        } else {
            builder.append(element)
        }
    }
    return builder.toString()
}

private fun isNonstandardDigit(ch: Char): Boolean {
    return Character.isDigit(ch) && ch !in '0'..'9'
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}
