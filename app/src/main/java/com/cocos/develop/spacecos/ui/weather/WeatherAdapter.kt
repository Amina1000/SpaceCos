package com.cocos.develop.spacecos.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cocos.develop.spacecos.data.DonkiCmeResponseData
import com.cocos.develop.spacecos.databinding.ItemWeatherBinding

class WeatherAdapter :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder?>() {

    private val donkiList = ArrayList<DonkiCmeResponseData>()
    private var _binding: ItemWeatherBinding? = null
    private val binding
        get() = _binding!!

    fun addItems(donkiData: ArrayList<DonkiCmeResponseData>) {
        this.donkiList.addAll(donkiData)
        notifyDataSetChanged()
    }

    fun clear() = donkiList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        _binding = ItemWeatherBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding.root as View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(donkiList[position])
    }

    override fun getItemCount() = donkiList.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(donki: DonkiCmeResponseData) {
            with(binding) {
                itemView.apply {
                    donki.activityID?.let {
                        activityID.text = it
                    }

                    donki.link?.let {
                        link.text = it
                    }

                    donki.startTime?.let {
                        startTime.text = it
                    }

                    donki.note?.let {
                        note.setText(it)
                    }

                }
            }

        }
    }
}