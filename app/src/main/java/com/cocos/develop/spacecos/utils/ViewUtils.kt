package com.cocos.develop.spacecos.utils

import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import kotlinx.android.synthetic.main.loading_layout.*

/**
 * homework com.cocos.develop.spacecos.utils
 *
 * @author Amina
 * 12.09.2021
 */
//Задержка анимации
const val ANIMATION_DURATION = 200L
const val ANIMATION_START_DELAY = 300L
const val TWO = 2
const val RADIUS = 0.5f

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

fun AppCompatImageButton.animateWithCircularReveal(){
    val cx = this.width / TWO
    val cy = this.height / TWO
    val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, finalRadius, RADIUS)
    anim.duration = ANIMATION_START_DELAY
    anim.start()
}

fun View.showViewWorking() {
    this.visibility = View.GONE
}

fun View.showViewLoading() {
    this.visibility = View.VISIBLE
}