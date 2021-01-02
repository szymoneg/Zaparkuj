package com.example.parkIt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.ReservationItem
import kotlinx.android.synthetic.main.reservations_item.view.*

class ReservationsAdapter(private val reservationList: List<ReservationItem>) :
    RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.reservations_item, parent, false)
        return ReservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val currentItem = reservationList[position]

        holder.idReservation.text = currentItem.idReservation
        holder.address.text = currentItem.address
        holder.carBrand.text = currentItem.carBrand
        holder.license.text = currentItem.license
        holder.dateEnd.text = currentItem.dateEnd
    }

    override fun getItemCount() = reservationList.size

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idReservation: TextView = itemView.textIdReservation
        val address: TextView = itemView.textAddress
        val carBrand: TextView = itemView.textCarBrandRes
        val license: TextView = itemView.textLicenseRes
        val dateEnd: TextView = itemView.textDateEnd
    }

}
