package com.example.parkIt

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.SpotItem
import kotlinx.android.synthetic.main.spot_item.view.*

class SpotsAdapter(private val spotList: List<SpotItem>) :
    RecyclerView.Adapter<SpotsAdapter.SpotViewHolder>() {

    val colW =  Color.parseColor("#ffffff")
    val colB = Color.parseColor("#000000")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.spot_item, parent, false)
        return SpotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        val currentItem = spotList[position]
        if (!currentItem.isFree) {
            holder.spotBtn.isClickable = false
            holder.spotBtn.backgroundTintList =
                ContextCompat.getColorStateList(holder.spotBtn.context, R.color.red_back)
            holder.spotBtn.setTextColor(colW)
        } else {
            holder.spotBtn.isClickable = true
            holder.spotBtn.backgroundTintList =
                ContextCompat.getColorStateList(holder.spotBtn.context, R.color.green_back)
            holder.spotBtn.setTextColor(colB)
        }
        holder.spotBtn.text = currentItem.spotName
    }

    override fun getItemCount() = spotList.size

    class SpotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spotBtn: Button = itemView.btnSpot
    }
}
