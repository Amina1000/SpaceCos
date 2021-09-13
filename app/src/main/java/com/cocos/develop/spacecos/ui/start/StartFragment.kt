package com.cocos.develop.spacecos.ui.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.ui.common.Controller
import com.cocos.develop.spacecos.ui.main.MainFragment
import com.cocos.develop.spacecos.ui.nasa.NasaActivity
import com.cocos.develop.spacecos.ui.settings.SettingsFragment
import com.cocos.develop.spacecos.utils.*
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private var show = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        //Запустим анимацию с задержкой, чтобы стартануть после отображения фрагмента
        Thread {
            Handler(Looper.getMainLooper()).postDelayed({
                showComponents()
                /*
               Навешаем кликлистенеры тоже позже, после того как загрузится фрагмент, в котором есть эти кнопки
                */
                setButtonListener()
            }, ANIMATION_START_DELAY)
        }.start()
    }

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.fragment_start_end_animation)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint_container, transition)
        constraintSet.applyTo(constraint_container)
    }
    //Навешаем кликлистенеры
    private fun setButtonListener() {
        btn_main.setOnClickListener {
            btn_main.animateWithCircularReveal()
            Thread {
                Handler(Looper.getMainLooper()).postDelayed({
                    (requireActivity() as Controller).openSettingsScreen(MainFragment.newInstance())
                }, ANIMATION_DURATION)
            }.start()
        }

        btn_nasa.setOnClickListener {
            btn_nasa.animateWithCircularReveal()
            Thread {
                Handler(Looper.getMainLooper()).postDelayed({
                    activity?.let { startActivity(Intent(it, NasaActivity::class.java)) }
                }, ANIMATION_DURATION)
            }.start()
        }
        btn_settings.setOnClickListener {
            btn_settings.animateWithCircularReveal()
            Thread {
                Handler(Looper.getMainLooper()).postDelayed({
                    (requireActivity() as Controller).openSettingsScreen(SettingsFragment())
                }, ANIMATION_DURATION)
            }.start()
        }

    }
}