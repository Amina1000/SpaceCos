package com.cocos.develop.spacecos.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.cocos.develop.spacecos.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }


}