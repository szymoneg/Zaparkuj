package com.example.parkIt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.ReservationItem
import com.example.parkIt.web.ConnectionAPI
import kotlinx.android.synthetic.main.reservations_item.view.*
import java.security.Timestamp
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
        val currentItem = reservationList[position]
        val conn: ConnectionAPI = ConnectionAPI()

        val sdf = SimpleDateFormat("dd-MM-yy hh:mm")
        val netDate = Date(currentItem.dateEnd.time)
        val date = sdf.format(netDate)

        holder.idReservation.text = currentItem.placeName
        holder.address.text = currentItem.parkingAddress
        holder.carBrand.text = currentItem.carMark
        holder.license.text = currentItem.licencePlate
        holder.dateEnd.text = date
        holder.image.setImageResource(R.drawable.ic_remove)

        holder.image.setOnClickListener {
            conn.deleteReservation(reservationList[position].idReservation)
            //TODO do usunieca sleep
            Thread.sleep(500)
            //TODO odswiezanie rezerwacji
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    override fun getItemCount() = reservationList.size

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idReservation: TextView = itemView.textIdReservation
        val address: TextView = itemView.textAddress
        val carBrand: TextView = itemView.textCarBrandRes
        val license: TextView = itemView.textLicenseRes
        val dateEnd: TextView = itemView.textDateEnd
        val image: ImageView = itemView.imageRemove
    }

}
