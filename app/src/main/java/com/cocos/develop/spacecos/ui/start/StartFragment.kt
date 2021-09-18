package com.cocos.develop.spacecos.ui.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.databinding.FragmentStartBinding
import com.cocos.develop.spacecos.ui.common.Controller
import com.cocos.develop.spacecos.ui.main.MainFragment
import com.cocos.develop.spacecos.ui.nasa.NasaActivity
import com.cocos.develop.spacecos.ui.settings.SettingsFragment
import com.cocos.develop.spacecos.utils.ANIMATION_DURATION
import com.cocos.develop.spacecos.utils.ANIMATION_START_DELAY
import com.cocos.develop.spacecos.utils.animateWithCircularReveal

class StartFragment : Fragment() {

    private var show = false
    private val binding: FragmentStartBinding by viewBinding(FragmentStartBinding::bind)

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

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }

    //Навешаем кликлистенеры
    private fun setButtonListener() {
        with(binding) {
            btnMain.setOnClickListener {
                btnMain.animateWithCircularReveal()
                Thread {
                    Handler(Looper.getMainLooper()).postDelayed({
                        (requireActivity() as Controller).openSettingsScreen(MainFragment.newInstance())
                    }, ANIMATION_DURATION)
                }.start()
            }
            btnNasa.setOnClickListener {
                btnNasa.animateWithCircularReveal()
                Thread {
                    Handler(Looper.getMainLooper()).postDelayed({
                        activity?.let { startActivity(Intent(it, NasaActivity::class.java)) }
                    }, ANIMATION_DURATION)
                }.start()
            }
            btnSettings.setOnClickListener {
                btnSettings.animateWithCircularReveal()
                Thread {
                    Handler(Looper.getMainLooper()).postDelayed({
                        (requireActivity() as Controller).openSettingsScreen(SettingsFragment())
                    }, ANIMATION_DURATION)
                }.start()
            }
        }

    }
}