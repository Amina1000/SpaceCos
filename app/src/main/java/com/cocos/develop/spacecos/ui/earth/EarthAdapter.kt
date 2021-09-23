package com.cocos.develop.spacecos.ui.earth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.databinding.ItemEarthBinding
import com.cocos.develop.spacecos.utils.picScaleAnimation


class EarthAdapter :
    RecyclerView.Adapter<EarthAdapter.ViewHolder?>() {

    private val earthsList = ArrayList<EpicResponseData>()
    private val IDENTIFIER = "Identifier: "
    private var _binding: ItemEarthBinding? = null

    private val binding
        get() = _binding!!

    fun addItems(epicList: ArrayList<EpicResponseData>) {
        earthsList.addAll(epicList)
        notifyDataSetChanged()
    }

    fun clear() = earthsList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        _binding = ItemEarthBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding.root as View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(earthsList[position])
    }

    override fun getItemCount() = earthsList.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(earth: EpicResponseData) {
            var isExpanded = false
            with(binding) {
                itemView.apply {
                    earth.caption?.let {
                        caption.text = it
                    }

                    earth.identifier?.let {
                        (IDENTIFIER + it).also { identifier.text = it }
                    }

                    earth.date?.let {
                        date.text = it
                    }

                    earth.pathPicture?.let {
                        epicImageView.load(it) {
                            context
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.bg_earth)
                        }
                        epicImageView.setOnClickListener {
                            isExpanded = !isExpanded
                            epicImageView.picScaleAnimation(isExpanded,binding.earthContainer)
                        }

                    }

                }
            }

        }
    }

}