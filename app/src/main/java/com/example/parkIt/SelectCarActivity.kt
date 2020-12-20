package com.example.parkIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cars_recycler.*

class SelectCarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cars_recycler)
        val exampleList = generateDummyList()

        recycle_cars.adapter = CarsAdapter(exampleList)
        recycle_cars.layoutManager = LinearLayoutManager(this)
        recycle_cars.setHasFixedSize(true)
    }
    private fun generateDummyList(): List<CarItem> {
        val list = ArrayList<CarItem>()


            list += CarItem("Citroen Berlingo", "KBR1234")
            list += CarItem("Fiat Multipla", "KTE20301")
            list += CarItem("Mazda Xd", "KT213231")
            list += CarItem("Fiat Punto", "KR23100")
            list += CarItem("Opel Astra", "KL2130")
            list += CarItem("Toyota Rav 3", "KBR3124")

        return list
    }
}