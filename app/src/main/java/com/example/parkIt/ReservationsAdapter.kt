package com.example.parkIt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.ReservationItem
import kotlinx.android.synthetic.main.reservations_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ReservationsAdapter(private val reservationList: Array<ReservationItem>) :
    RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.reservations_item, parent, false)
        return ReservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val currentItem = reservationList[position]

        holder.idReservation.text = currentItem.placeName
        holder.address.text = currentItem.parkingAddress
        holder.carBrand.text = currentItem.carMark
        holder.license.text = currentItem.licencePlate
        //holder.dateEnd.text = LocalDate.parse(Date.parse(currentItem.dateEnd.toString()).toString(),formatter).toString();
        holder.dateEnd.text = currentItem.dateEnd.toString()
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
