package com.cocos.develop.spacecos.ui.common

/**
 * homework com.cocos.develop.spacecos.ui.weather
 *
 * @author Amina
 * 18.09.2021
 */
interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)

}

interface ItemTouchHelperViewHolder {

    fun onItemSelected()

    fun onItemClear()

}