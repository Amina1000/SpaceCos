package com.cocos.develop.spacecos.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.DonkiCmeResponseData
import com.cocos.develop.spacecos.databinding.FragmentWeatherBinding
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.utils.toast

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    private val binding: FragmentWeatherBinding by viewBinding(FragmentWeatherBinding::bind)
    private val adapter by lazy { WeatherAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getEarthPicture().observe(viewLifecycleOwner) { renderData(it) }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }

    private fun initRV() {
        val recyclerMars: RecyclerView = binding.donkiList
        recyclerMars.adapter = adapter
    }

    private fun renderData(data: AppStates) {
        when (data) {
            is AppStates.Success<*> -> {
                val serverResponseData = data.serverResponseData as ArrayList<DonkiCmeResponseData>
                serverResponseData.let {
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