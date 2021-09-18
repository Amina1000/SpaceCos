package com.cocos.develop.spacecos.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.DonkiCmeResponseData
import com.cocos.develop.spacecos.data.NoteType
import kotlinx.android.synthetic.main.item_link.view.*
import kotlinx.android.synthetic.main.item_weather.view.*

private const val TYPE_NOTE = 0
private const val TYPE_LINK = 1

class WeatherAdapter :
    RecyclerView.Adapter<WeatherAdapter.BaseViewHolder?>() {

    private val donkiList = ArrayList<Pair<NoteType, DonkiCmeResponseData>>()


    fun addItems(donkiData: ArrayList<DonkiCmeResponseData>) {
        donkiData.forEach {
            donkiList.add(Pair(NoteType.NOTE, it))
            donkiList.add(Pair(NoteType.LINK, it))
        }
        notifyDataSetChanged()
    }

    fun clear() = donkiList.clear()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> NoteViewHolder(
                inflater.inflate(
                    R.layout.item_weather,
                    parent,
                    false
                ) as View
            )
            else -> LinkViewHolder(inflater.inflate(R.layout.item_link, parent, false) as View)
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NOTE) {
            holder as NoteViewHolder
            holder.bind(donkiList[position])
        } else {
            holder as LinkViewHolder
            holder.bind(donkiList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (donkiList[position].first) {
            NoteType.NOTE -> TYPE_NOTE
            else -> TYPE_LINK
        }

    }

    override fun getItemCount() = donkiList.size

    inner class NoteViewHolder(
        itemView: View
    ) : BaseViewHolder(itemView) {

        override fun bind(donki: Pair<NoteType, DonkiCmeResponseData>) {

            itemView.apply {
                donki.second.activityID?.let {
                    activityID.text = it
                }
                donki.second.startTime?.let {
                    startTime.text = it
                }
                donki.second.note?.let {
                    note.setText(it)
                }
                remove_item.setOnClickListener { removeItem() }
                move_item_up.setOnClickListener { moveUp() }
                move_item_down.setOnClickListener { moveDown() }
            }

        }

        private fun removeItem() {
            donkiList.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                donkiList.removeAt(currentPosition).apply {
                    donkiList.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < donkiList.size - 1 }?.also { currentPosition ->
                donkiList.removeAt(currentPosition).apply {
                    donkiList.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

    }

    inner class LinkViewHolder(
        itemView: View
    ) : BaseViewHolder(itemView) {

        override fun bind(donki: Pair<NoteType, DonkiCmeResponseData>) {
            itemView.apply {
                donki.second.link?.let {
                    link.setText(it)
                }
            }
        }
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(donki: Pair<NoteType, DonkiCmeResponseData>)
    }
}