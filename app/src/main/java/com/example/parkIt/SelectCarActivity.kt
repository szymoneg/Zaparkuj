package com.example.parkIt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkIt.data.CarItem
import com.example.parkIt.data.Parkings
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cars_recycler.*
import okhttp3.*
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.OverlayItem
import java.io.IOException

class SelectCarActivity : AppCompatActivity() {
    private lateinit var username: String;
    private lateinit var jwtToken: String;
    private lateinit var arrCars: Array<CarItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cars_recycler)

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("SearchKey","XD").toString();
        jwtToken = sharedPreferences.getString("Key","XD").toString();

        getCars()
        Thread.sleep(500)

        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "My cars"

        val addCarButton = findViewById<ImageView>(R.id.add_car_plus)

        addCarButton.setOnClickListener {
            val intent = Intent(this@SelectCarActivity, AddCarActivity::class.java)
            startActivity(intent)
        }

        recycle_cars.adapter = CarsAdapter(arrCars)
        recycle_cars.layoutManager = LinearLayoutManager(this)
        recycle_cars.setHasFixedSize(true)
    }

    fun getCars() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/cars/$username")
            //.addHeader("Authorization", "Bearer $jwtToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }
                    if (response.code == 200) {
                        val dataJson = response.body?.string();
                        val gson = Gson()
                        val enums: Array<CarItem> = gson.fromJson(
                            dataJson,
                            Array<CarItem>::class.java
                        )
                        arrCars = enums;
                    } else {
                        Log.e("----Edit:", response.code.toString())
                    }
                }
            }
        })
    }
}