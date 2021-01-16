package com.example.parkIt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.parkIt.data.CarItem
import com.example.parkIt.data.SpotItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.spots_recycler.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class SelectSpotActivity : AppCompatActivity() {
    private lateinit var jwtToken: String
    private lateinit var dateBegin: String
    private lateinit var dateEnd: String
    private var idSector: Int = 0

    private lateinit var arrSpots: Array<SpotItem>
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spots_recycler)

        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Parking places"

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        jwtToken = sharedPreferences.getString("SearchKey","XD").toString()
        dateBegin = sharedPreferences.getString("start","XD").toString()
        dateEnd = sharedPreferences.getString("end","XD").toString()
        idSector = sharedPreferences.getInt("SectorID",2137)

        sendMessage(idSector, dateBegin, dateEnd)
        Thread.sleep(200)

        val exampleList = arrSpots
        recycle_spot.adapter = SpotsAdapter(exampleList.toList(),this)
        recycle_spot.layoutManager = GridLayoutManager(this, 2)
        recycle_spot.setHasFixedSize(true)
    }

    fun sendMessage(idSector: Int, dateBegin: String, dateEnd: String) {
        val url = "http://10.0.2.2:8080/place/countplaces/$idSector"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("dateBegin", dateBegin)
        rootObject.put("dateEnd", dateEnd)

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.i("Response code: ", response.code.toString())
                if (response.code.toString() == "200"){
                    val dataJson = response.body?.string();
                    val gson = Gson()
                    val enums: Array<SpotItem> = gson.fromJson(
                        dataJson,
                        Array<SpotItem>::class.java
                    )
                    arrSpots = enums
                }else{
                    Log.e("Error", response.code.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                print(e.printStackTrace())
            }
        })
    }
}