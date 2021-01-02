package com.example.parkIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.ReservationItem
import com.example.parkIt.data.SectorItem
import kotlinx.android.synthetic.main.reservations_recycler.*
import kotlinx.android.synthetic.main.sectors_recycler.*

class SelectSectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sectors_recycler)
        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Nazwa Ulicy" //TODO zmieniac po klikniecu na mapie

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