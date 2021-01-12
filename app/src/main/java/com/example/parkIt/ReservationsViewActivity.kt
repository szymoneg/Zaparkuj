package com.example.parkIt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.CarItem
import com.example.parkIt.data.ReservationItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cars_recycler.*
import kotlinx.android.synthetic.main.cars_recycler.recycle_cars
import kotlinx.android.synthetic.main.reservations_recycler.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.io.IOException

class ReservationsViewActivity : AppCompatActivity() {
    private lateinit var username: String;
    private lateinit var jwtToken: String;
    private lateinit var arrReservations: Array<ReservationItem>

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservations_recycler)
        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Reservations"

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("SearchKey","XD").toString();
        jwtToken = sharedPreferences.getString("Key","XD").toString();

        getReservations()

        //TODO sleep
        Thread.sleep(1000)

        val exampleList = arrReservations;
        recycle_reservation.adapter = ReservationsAdapter(exampleList)
        recycle_reservation.layoutManager = LinearLayoutManager(this)
        recycle_reservation.setHasFixedSize(true)
    }



     fun getReservations(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/reservation/user/$username")
            //.addHeader("Authorization", "Bearer $jwtToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            // TODO: 20.12.2020 regex to do
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }
                    if (response.code == 200) {
                        Log.i("No działą! ", "XDDD")
                        val dataJson = response.body?.string();
                        val gson = Gson()
                        val enums: Array<ReservationItem> = gson.fromJson(
                            dataJson,
                            Array<ReservationItem>::class.java
                        )
                        runOnUiThread {
                            Log.i("XDD", enums.get(1).carMark)
                        }
                        arrReservations = enums;
                    } else {
                        Log.e("----Edit:", response.code.toString())
                    }
                }
            }
        })
    }
}