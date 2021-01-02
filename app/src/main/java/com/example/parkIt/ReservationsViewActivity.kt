package com.example.parkIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.CarItem
import com.example.parkIt.data.ReservationItem
import kotlinx.android.synthetic.main.cars_recycler.*
import kotlinx.android.synthetic.main.cars_recycler.recycle_cars
import kotlinx.android.synthetic.main.reservations_recycler.*

class ReservationsViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservations_recycler)
        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Reservations"

        val exampleList = generateDummyList()
        recycle_reservation.adapter = ReservationsAdapter(exampleList)
        recycle_reservation.layoutManager = LinearLayoutManager(this)
        recycle_reservation.setHasFixedSize(true)
    }

    private fun generateDummyList(): List<ReservationItem> {
        val list = ArrayList<ReservationItem>()


        list += ReservationItem("021J", "Tarnow ul. krakowska 10","Citroen", "KBR1234", "03.01.2021T17:30")
        list += ReservationItem("023W", "Tarnow ul. krakowska 11","Citroen", "KBR1334", "07.01.2021T17:30")
        list += ReservationItem("021X", "Tarnow ul. krakowska 12","Mazda", "KBR1534", "05.01.2021T17:30")

        return list
    }
}