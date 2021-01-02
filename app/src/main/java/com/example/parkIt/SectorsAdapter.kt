package com.example.parkIt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.SectorItem
import kotlinx.android.synthetic.main.sector_item.view.*

class SectorsAdapter(private val sectorList: List<SectorItem>) :
    RecyclerView.Adapter<SectorsAdapter.SectorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sector_item, parent, false)
        return SectorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SectorViewHolder, position: Int) {
        val currentItem = sectorList[position]
        val slots = currentItem.availableSpot + " Spots"
        holder.sectorId.text = currentItem.sectorId
        holder.availableSpot.text = slots
        holder.image.setImageResource(R.drawable.ic_forward)
    }

    override fun getItemCount() = sectorList.size

    class SectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectorId: TextView = itemView.textSectorId
        val availableSpot: TextView = itemView.textSpots
        val image: ImageView = itemView.imageGoForward
    }
}