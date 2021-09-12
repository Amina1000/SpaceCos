package com.cocos.develop.spacecos.ui.nasa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.datasource.FragmentFactoryNasa
import com.cocos.develop.spacecos.databinding.ActivityApiBinding
import com.cocos.develop.spacecos.ui.earth.EarthFragment
import com.cocos.develop.spacecos.ui.main.MainActivity
import com.cocos.develop.spacecos.ui.mars.MarsFragment
import com.cocos.develop.spacecos.ui.weather.WeatherFragment
import com.cocos.develop.spacecos.utils.getAppSettings
import com.google.android.material.tabs.TabLayoutMediator

/**
 * homework com.cocos.develop.spaces.ui.api
 *
 * @author Amina
 * 04.09.2021
 */
class NasaActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityApiBinding::bind)
    private val fragments by lazy { listFragments() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        setAppSettings()
        initViewPage()
        initNavigationMenu()
    }

    private fun setAppSettings() {
        val spaceTheme = getAppSettings(application)
        if (spaceTheme) {
            setTheme(R.style.SpaceThemeStyle)
        } else {
            setTheme(R.style.BaseThemeStyle)
        }
    }
   private fun initNavigationMenu() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_home -> {
                    startActivity(Intent(this@NasaActivity, MainActivity::class.java))
                    true
                }
                R.id.bottom_view_favorite -> {
                    Toast.makeText(this@NasaActivity, getString(R.string.favourite), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

    }

    private fun initViewPage() {
        with(binding.viewPager) {
            adapter = ViewPagerAdapter(this@NasaActivity, fragments)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            fragments[pos].let {
                tab.text = it.title
                tab.setIcon(it.iconResId)
                it.badgeNumber?.let { number ->
                    tab.orCreateBadge.number = number
                }
            }
        }.attach()
    }

    private fun listFragments() = listOf(
        FragmentFactoryNasa(
            getString(R.string.earth),
            R.drawable.ic_earth
        ) { EarthFragment.newInstance() },
        FragmentFactoryNasa(
            getString(R.string.mars),
            R.drawable.ic_mars
        ) { MarsFragment.newInstance() },
        FragmentFactoryNasa(
            getString(R.string.weather),
            R.drawable.ic_system
        ) { WeatherFragment.newInstance() }
    )
}