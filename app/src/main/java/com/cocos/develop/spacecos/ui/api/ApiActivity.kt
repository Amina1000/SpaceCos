package com.cocos.develop.spacecos.ui.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.datasource.FragmentFactoryApi
import com.cocos.develop.spacecos.databinding.ActivityApiBinding
import com.cocos.develop.spacecos.ui.earth.EarthFragment
import com.cocos.develop.spacecos.ui.mars.MarsFragment
import com.cocos.develop.spacecos.ui.weather.WeatherFragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * homework com.cocos.develop.spaces.ui.api
 *
 * @author Amina
 * 04.09.2021
 */
class ApiActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityApiBinding::bind)
    private val fragments by lazy { listFragments() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        with(binding.viewPager) {
            adapter = ViewPagerAdapter(this@ApiActivity, fragments)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            fragments[pos].let {
                tab.text = it.title
                tab.setIcon(it.iconResId)
                 it.badgeNumber?.let {number->
                     tab.orCreateBadge.number = number
                 }
            }
        }.attach()

    }

    private fun listFragments() = listOf(
        FragmentFactoryApi(
            getString(R.string.earth),
            R.drawable.ic_earth
        ) { EarthFragment.newInstance() },
        FragmentFactoryApi(
            getString(R.string.mars),
            R.drawable.ic_mars
        ) { MarsFragment.newInstance() },
        FragmentFactoryApi(
            getString(R.string.weather),
            R.drawable.ic_system
        ) { WeatherFragment.newInstance() }
    )
}