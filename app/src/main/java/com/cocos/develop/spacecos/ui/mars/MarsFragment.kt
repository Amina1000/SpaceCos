package com.cocos.develop.spacecos.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.databinding.FragmentMarsBinding
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.utils.showViewLoading
import com.cocos.develop.spacecos.utils.showViewWorking
import com.cocos.develop.spacecos.utils.toast

class MarsFragment : Fragment() {

    companion object {
        fun newInstance() = MarsFragment()
    }

    private lateinit var viewModel: MarsViewModel
    private val binding: FragmentMarsBinding by viewBinding(FragmentMarsBinding::bind)
    private val adapter by lazy { MarsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarsViewModel::class.java)
        viewModel.getMarsPicture().observe(viewLifecycleOwner) { renderData(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }

    private fun initRV() {
        val recyclerMars: RecyclerView = binding.marsList
        recyclerMars.adapter = adapter
    }

    private fun renderData(data: AppStates) {
        when (data) {
            is AppStates.Success<*> -> {
                binding.loadingLayout.loadingLayoutContainer.showViewWorking()
                val serverResponseData = data.serverResponseData as MarsResponseData
                serverResponseData.let {
                    adapter.clear()
                    adapter.addItems(it)
                }
            }
            is AppStates.Loading -> {
                binding.loadingLayout.loadingLayoutContainer.showViewLoading()
            }
            is AppStates.Error -> {
                binding.loadingLayout.loadingLayoutContainer.showViewWorking()
                toast(data.error.message)
            }
        }
    }

}