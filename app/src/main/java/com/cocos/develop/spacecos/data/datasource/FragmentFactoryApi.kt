package com.cocos.develop.spacecos.data.datasource

import androidx.fragment.app.Fragment

/**
 * homework com.cocos.develop.spacecos.data.datasource
 *
 * @author Amina
 * 05.09.2021
 */
class FragmentFactoryApi(
    val title: String,
    val iconResId: Int,
    val badgeNumber: Int?=null,
    val factoryMethod: ()->Fragment
)