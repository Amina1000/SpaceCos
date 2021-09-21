package com.cocos.develop.spacecos.ui.mars

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.MarsEntity
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.databinding.ItemMarsBinding
import com.cocos.develop.spacecos.utils.picScaleAnimation

class MarsAdapter :
    RecyclerView.Adapter<MarsAdapter.ViewHolder?>() {

    private val marsList = ArrayList<MarsEntity>()
    private var _binding: ItemMarsBinding? = null
    private val binding
        get() = _binding!!

    fun addItems(marsResponseData: MarsResponseData) {
        marsResponseData.photoList?.let {
            marsList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() = marsList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        _binding = ItemMarsBinding.inflate(layoutInflater, parent, false)
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
            var isExpanded = false
            with(binding) {
                itemView.apply {

                    mars.sol?.let {
                        val spannable = SpannableStringBuilder(it.toString())
                        spannable.setSpan(
                            ForegroundColorSpan(Color.RED), 0, spannable.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        sol.text = spannable
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
                    imageView.setOnClickListener {
                        isExpanded = !isExpanded
                        imageView.picScaleAnimation(isExpanded, binding.marsContainer)
                    }
                }

            }
        }

    }
}
