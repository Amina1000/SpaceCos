package com.cocos.develop.spacecos.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.PodServerResponseData
import com.cocos.develop.spacecos.databinding.FragmentMainBinding
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.ui.common.Controller
import com.cocos.develop.spacecos.ui.nasa.NasaActivity
import com.cocos.develop.spacecos.ui.navigation.BottomNavigationDrawerFragment
import com.cocos.develop.spacecos.ui.settings.SettingsFragment
import com.cocos.develop.spacecos.utils.picScaleAnimation
import com.cocos.develop.spacecos.utils.toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.main_activity.*

private const val WIKI_URL = "https://en.wikipedia.org/wiki/"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private var isMain = true
    }

    //Ленивая инициализация модели
    private lateinit var viewModel: MainViewModel
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse(WIKI_URL + binding.inputEditText.text.toString())
            })
        }
        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            binding.imageView.picScaleAnimation(isExpanded, binding.apodMotion)
        }

        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.bottomSheetLayout.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        setBottomAppBar()
    }

    private fun setBottomAppBar() {
        with(binding) {
            val context = activity as MainActivity
            context.setSupportActionBar(bottomAppBar)
            setHasOptionsMenu(true)
            fab.setOnClickListener {
                if (isMain) {
                    isMain = false
                    binding.bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_search)
                } else {
                    isMain = true
                    bottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getPictureOfTheDay().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(data: AppStates) {
        when (data) {
            is AppStates.Success<*> -> {
                val serverResponseData = data.serverResponseData as PodServerResponseData
                serverResponseData.title?.let {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, getString(R.string.favourite), Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> (requireActivity() as Controller).openSettingsScreen(SettingsFragment())
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_api -> activity?.let { startActivity(Intent(it, NasaActivity::class.java)) }
        }
        return super.onOptionsItemSelected(item)
    }

}