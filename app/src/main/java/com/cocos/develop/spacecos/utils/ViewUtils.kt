package com.cocos.develop.spacecos.utils

import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet

/**
 * homework com.cocos.develop.spacecos.utils
 *
 * @author Amina
 * 12.09.2021
 */
fun AppCompatImageView.picScaleAnimation(isExpanded:Boolean, container:ViewGroup) {

    TransitionManager.beginDelayedTransition(
        container, TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeImageTransform())
    )

    val params: ViewGroup.LayoutParams = this.layoutParams
    params.height =
        if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
    this.layoutParams = params
    this.scaleType =
        if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
}