package com.cocos.develop.spacecos.ui.earth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.data.PodServerResponseData
import com.cocos.develop.spacecos.databinding.FragmentEarthBinding
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.utils.toast
import com.google.android.material.snackbar.Snackbar

class EarthFragment : Fragment() {

    companion object {
        fun newInstance() = EarthFragment()
    }

    private lateinit var viewModel: EarthViewModel
    private val binding: FragmentEarthBinding by viewBinding(FragmentEarthBinding::bind)
    private val adapter by lazy { EarthAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EarthViewModel::class.java)
        viewModel.getEarthPicture().observe(viewLifecycleOwner) { renderData(it) }
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }
    private fun initRV() {
        val recyclerEarth: RecyclerView = binding.earthList
        recyclerEarth.adapter = adapter
    }

    private fun renderData(data: AppStates) {
        when (data) {
            is AppStates.Success<*> -> {
                val serverResponseData = data.serverResponseData as ArrayList<EpicResponseData>
                serverResponseData?.let {
                    adapter.clear()
                    adapter.addItems(it)
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

}