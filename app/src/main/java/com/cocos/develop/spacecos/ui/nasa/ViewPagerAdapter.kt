package com.cocos.develop.spacecos.ui.nasa

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cocos.develop.spacecos.data.datasource.FragmentFactoryNasa

/**
 * homework com.cocos.develop.spacecos.ui.api
 *
 * @author Amina
 * 04.09.2021
 */

class ViewPagerAdapter(fragmentActivity: FragmentActivity,
                       private val fragments: List<FragmentFactoryNasa> = emptyList()) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment {
        return fragments[position].factoryMethod()
    }


}
