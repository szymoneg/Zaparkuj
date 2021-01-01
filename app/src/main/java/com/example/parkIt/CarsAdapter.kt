package com.example.parkIt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkIt.data.CarItem
import kotlinx.android.synthetic.main.cars_item.view.*

class CarsAdapter(private val carList: List<CarItem>) : RecyclerView.Adapter<CarsAdapter.CarViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cars_item, parent, false)
        return  CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentItem = carList[position]

        holder.carBrand.text = currentItem.carBrand
        holder.carModel.text = currentItem.carModel
        holder.license.text = currentItem.license
        holder.image.setImageResource(R.drawable.ic_settings)
    }

    override fun getItemCount() = carList.size

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val carBrand : TextView = itemView.textCarBrand
        val carModel : TextView = itemView.textCarModel
        val license : TextView = itemView.textLicense
        val image : ImageView = itemView.imageSettings
    }
}