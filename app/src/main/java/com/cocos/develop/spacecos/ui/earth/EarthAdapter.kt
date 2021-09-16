package com.cocos.develop.spacecos.ui.earth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.utils.picScaleAnimation
import kotlinx.android.synthetic.main.item_earth.view.*

class EarthAdapter :
    RecyclerView.Adapter<EarthAdapter.ViewHolder?>() {

    private val earthsList = ArrayList<EpicResponseData>()
    private val IDENTIFIER = "Identifier: "

    fun addItems(epicList: ArrayList<EpicResponseData>) {
        earthsList.addAll(epicList)
        notifyDataSetChanged()
    }

    fun clear() = earthsList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_earth, parent, false) as View)
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
                        image_view.load(it) {
                            context
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.bg_earth)
                        }
                        image_view.setOnClickListener {
                            isExpanded = !isExpanded
                            image_view.picScaleAnimation(isExpanded,earth_container)
                        }

                }
            }

        }
    }
}