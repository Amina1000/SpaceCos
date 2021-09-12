package com.cocos.develop.spacecos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.ui.settings.SettingsFragment
import com.cocos.develop.spacecos.utils.getAppSettings

class MainActivity : AppCompatActivity(), MainFragment.Controller {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppSettings()
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            loadFragment(MainFragment.newInstance())
        }
    }

    private fun setAppSettings() {
        val spaceTheme = getAppSettings(application)
        if (spaceTheme) {
            setTheme(R.style.SpaceThemeStyle)
        } else {
            setTheme(R.style.BaseThemeStyle)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun openSettingsScreen() {
        loadFragment(SettingsFragment())
    }
}