package com.cocos.develop.spacecos.ui.mars


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.MarsEntity
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.databinding.ItemMarsBinding

/****
Project Nasa Photo Day
Package softing.ubah4ukdev.nasaphotoday.ui.pictureearth

Created by Ivan Sheynmaer

2021.07.12
v1.0
 */
class MarsAdapter :
    RecyclerView.Adapter<MarsAdapter.ViewHolder?>() {

    private val marsList = ArrayList<MarsEntity>()
    private lateinit var binding: ItemMarsBinding

    fun addItems(marsResponseData: MarsResponseData) {
        marsResponseData.photoList?.let {
            marsList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() = marsList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemMarsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding.root as View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(marsList[position])
    }

    override fun getItemCount() = marsList.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(mars: MarsEntity) {
            with(binding) {
                itemView.apply {

                    mars.sol?.let {
                        sol.text = it.toString()
                    }
                }

                mars.date?.let {
                    date.text = it
                }

                mars.image?.let {
                    imageView.load(it) {
                        itemView.context
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.bg_mars)
                    }
                }

            }
        }

    }
}
