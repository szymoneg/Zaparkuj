package com.example.parkIt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.ReservationItem
import com.example.parkIt.data.SectorItem
import kotlinx.android.synthetic.main.reservations_recycler.*
import kotlinx.android.synthetic.main.sectors_recycler.*

class SelectSectorActivity : AppCompatActivity() {
    private lateinit var username: String
    private lateinit var jwtToken: String
    private lateinit var idParking: String
    private lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sectors_recycler)
        val navBar = findViewById<TextView>(R.id.action_bar_text);

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("SearchKey","XD").toString();
        jwtToken = sharedPreferences.getString("Key","XD").toString();
        idParking = sharedPreferences.getString("parking","XD").toString()
        address = sharedPreferences.getString("address","XD").toString()

        navBar.text = address

        val exampleList = generateDummyList()
        recycle_sector.adapter = SectorsAdapter(exampleList)
        recycle_sector.layoutManager = LinearLayoutManager(this)
        recycle_sector.setHasFixedSize(true)
        //test
    }

    private fun generateDummyList(): List<SectorItem> {
        val list = ArrayList<SectorItem>()
        list += SectorItem("S01", "15")
        list += SectorItem("S02", "12")
        list += SectorItem("S03", "9")
        list += SectorItem("S04", "7")
        return list
    }

}