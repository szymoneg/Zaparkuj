package com.example.parkIt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cars_item.view.*

class CarsAdapter(private val carList: List<CarItem>) : RecyclerView.Adapter<CarsAdapter.CarViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cars_item, parent, false)
        return  CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentItem = carList[position]

        holder.car.text = currentItem.car
        holder.license.text = currentItem.license
        holder.image.setImageResource(R.drawable.ic_settings)
    }

    override fun getItemCount() = carList.size

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val car : TextView = itemView.textCar
        val license : TextView = itemView.textLicense
        val image : ImageView = itemView.imageSettings
    }
}