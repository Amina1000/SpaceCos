package com.cocos.develop.spacecos.utils

import android.app.Application
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * homework com.cocos.develop.spacecos.utils
 *
 * @author Amina
 * 06.09.2021
 */
fun getAppSettings(application: Application): Boolean {
    val preferenceManager = PreferenceManager.getDefaultSharedPreferences(application)
    return preferenceManager.getBoolean("select_theme", true)
}

fun yesterday(): String {
    var currentDate = Date()
    val calendar = Calendar.getInstance()
    calendar.time = currentDate
    calendar.add(Calendar.DATE, -2)
    currentDate = calendar.time
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(currentDate)
}

fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}