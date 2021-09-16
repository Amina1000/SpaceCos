package com.cocos.develop.spacecos.ui.mars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.MarsEntity
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.utils.picScaleAnimation
import kotlinx.android.synthetic.main.item_mars.view.*

class MarsAdapter :
    RecyclerView.Adapter<MarsAdapter.ViewHolder?>() {

    private val marsList = ArrayList<MarsEntity>()

    fun addItems(marsResponseData: MarsResponseData) {
        marsResponseData.photoList?.let {
            marsList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() = marsList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mars, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(marsList[position])
    }

    override fun getItemCount() = marsList.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(mars: MarsEntity) {
            var isExpanded = false

            itemView.apply {

                mars.sol?.let {
                    sol.text = it.toString()
                }

                mars.date?.let {
                    date.text = it
                }
                mars.image?.let {
                    image_view.load(it) {
                        itemView.context
                        kotlin.error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.bg_mars)
                    }
                    image_view.setOnClickListener {
                        isExpanded = !isExpanded
                        image_view.picScaleAnimation(isExpanded, mars_container)
                    }
                }
            }

        }

    }
}
