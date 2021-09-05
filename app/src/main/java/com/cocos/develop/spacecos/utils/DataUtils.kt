package com.cocos.develop.spacecos.utils

import android.app.Application
import androidx.preference.PreferenceManager

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