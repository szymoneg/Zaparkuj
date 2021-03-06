package com.example.parkIt

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.CarItem
import com.example.parkIt.web.ConnectionAPI
import kotlinx.android.synthetic.main.cars_item.view.*

class CarsAdapter(private val carList: Array<CarItem>) :
    RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {

    val web = ConnectionAPI()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cars_item, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentItem = carList[position]

        holder.carBrand.text = currentItem.mark
        holder.carModel.text = currentItem.model
        holder.license.text = currentItem.licencePlate
        holder.image.setImageResource(R.drawable.ic_remove)

        holder.image.setOnClickListener { v ->
            Log.i("ID samochjodu",carList[position].idCar.toString())
            web.deleteCar(carList[position].idCar)
            val intent = Intent(v.context, SelectCarActivity::class.java)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount() = carList.size

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carBrand: TextView = itemView.textCarBrand
        val carModel: TextView = itemView.textCarModel
        val license: TextView = itemView.textLicense
        val image: ImageView = itemView.imageSettings
    }
}