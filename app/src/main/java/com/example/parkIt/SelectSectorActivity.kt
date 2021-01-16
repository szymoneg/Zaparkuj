package com.example.parkIt

import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.CarItem
import com.example.parkIt.data.ReservationItem
import com.example.parkIt.data.SectorItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.reservations_recycler.*
import kotlinx.android.synthetic.main.sectors_recycler.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class SelectSectorActivity : AppCompatActivity()  {
    private lateinit var username: String
    private lateinit var jwtToken: String
    private lateinit var idParking: String
    private lateinit var address: String
    private lateinit var arrSectors: Array<SectorItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sectors_recycler)
        val navBar = findViewById<TextView>(R.id.action_bar_text);


        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("SearchKey","XD").toString();
        jwtToken = sharedPreferences.getString("Key","XD").toString();
        idParking = sharedPreferences.getString("parking","XD").toString()
        address = sharedPreferences.getString("address","XD").toString()
        val sectors = sharedPreferences.getString("Sectors","XD")
        val json = Gson()
        arrSectors = json.fromJson(sectors,Array<SectorItem>::class.java);

        navBar.text = address

        recycle_sector.adapter = SectorsAdapter(arrSectors.toList(),this)
        recycle_sector.layoutManager = LinearLayoutManager(this)
        recycle_sector.setHasFixedSize(true)
    }
}
