package com.example.parkIt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.CarItem
import kotlinx.android.synthetic.main.cars_recycler.*

class SelectCarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cars_recycler)
        val exampleList = generateDummyList()

        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "My cars"

        val addCarButton = findViewById<ImageView>(R.id.add_car_plus)

        addCarButton.setOnClickListener {
            val intent = Intent(this@SelectCarActivity, AddCarActivity::class.java)
            startActivity(intent)
        }

        recycle_cars.adapter = CarsAdapter(exampleList)
        recycle_cars.layoutManager = LinearLayoutManager(this)
        recycle_cars.setHasFixedSize(true)
    }

    private fun generateDummyList(): List<CarItem> {
        val list = ArrayList<CarItem>()


        list += CarItem("Citroen", "Berlingo", "KBR1234")
        list += CarItem("Fiat", "Multipla", "KTE20301")
        list += CarItem("Mazda", "Xd", "KT213231")
        list += CarItem("Fiat", "Punto", "KR23100")
        list += CarItem("Opel", "Astra", "KL2130")
        list += CarItem("Toyota", "Rav 3", "KBR3124")

        return list
    }
}