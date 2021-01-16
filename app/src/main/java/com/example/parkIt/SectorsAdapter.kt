package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.SectorItem
import kotlinx.android.synthetic.main.sector_item.view.*

class SectorsAdapter(private val sectorList: List<SectorItem>, context: Context) :
    RecyclerView.Adapter<SectorsAdapter.SectorViewHolder>() {
    val sharedPreferences = context.getSharedPreferences("SP", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sector_item, parent, false)
        return SectorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SectorViewHolder, position: Int) {

        val currentItem = sectorList[position]
        holder.sectorId.text = currentItem.sectorName.toString()
        holder.availableSpot.text = "${currentItem.occupatePlaces}/${currentItem.freePlaces+currentItem.occupatePlaces}";
        holder.image.setImageResource(R.drawable.ic_forward)

        holder.image.setOnClickListener { v->
            Log.i("Kod ", sectorList[position].sectorName)
            val editor = sharedPreferences.edit()
            editor.putInt("SectorID", sectorList[position].idSector)
            editor.apply()
            val intent = Intent(v.context, SelectSpotActivity::class.java)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount() = sectorList.size

    class SectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectorId: TextView = itemView.textSectorId
        val availableSpot: TextView = itemView.textSpots
        val image: ImageView = itemView.btnSector
    }
}