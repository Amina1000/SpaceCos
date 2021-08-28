package com.cocos.develop.spacecos.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.databinding.MainFragmentBinding
import com.cocos.develop.spacecos.domain.AppStates
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //Ленивая инициализация модели
    private lateinit var viewModel: MainViewModel
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(data: AppStates) {
        when (data) {
            is AppStates.Success -> {
                val serverResponseData = data.serverResponseData
                serverResponseData.title?.let{
                    binding.bottomSheetLayout.bottomSheetDescriptionHeader.text = it
                }
                serverResponseData.explanation?.let {
                    binding.bottomSheetLayout.bottomSheetDescription.text = it
                }
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    binding.imageView.load(url) {
                        lifecycle(this@MainFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is AppStates.Loading -> {
                //showLoading()
            }
            is AppStates.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}