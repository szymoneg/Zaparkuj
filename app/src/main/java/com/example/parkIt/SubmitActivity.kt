package com.example.parkIt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SubmitActivity : AppCompatActivity() {
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private lateinit var jwtToken: String
    private var idPlace: Int = 0
    private var idCar: Int = 0
    private lateinit var dateBegin: String
    private lateinit var dateEnd: String

    private lateinit var spotName: String
    private lateinit var carName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        val submitBtn = findViewById<Button>(R.id.buttonParkIt)

        val carNameView = findViewById<TextView>(R.id.textViewSubCar)
        val spotNameView = findViewById<TextView>(R.id.textViewSubSpot)
        val dateStartView = findViewById<TextView>(R.id.textViewSubStart)
        val dateEndView = findViewById<TextView>(R.id.textViewSubEnd)

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        jwtToken = sharedPreferences.getString("Key","XD").toString()
        idPlace = sharedPreferences.getInt("spotPlace",2137)
        idCar = sharedPreferences.getInt("car",2137)
        dateBegin = sharedPreferences.getString("start","XD").toString()
        dateEnd = sharedPreferences.getString("end","XD").toString()

        spotName = sharedPreferences.getString("spotName","XD").toString()
        carName = sharedPreferences.getString("carName","XD").toString()

        carNameView.text = carName
        spotNameView.text = spotName

        val dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        val dateXD: String = "yyyy-MM-dd HH:mm"

        val sdf = SimpleDateFormat(dateFormat)
        sdf.applyPattern(dateFormat)
        val dateBegin = sdf.parse(dateBegin)
        val dateEnd = sdf.parse(dateEnd)
        sdf.applyPattern(dateXD)
        val dateStarte = sdf.format(dateBegin)
        val dateEndoo = sdf.format(dateEnd)

        dateStartView.text = dateStarte
        dateEndView.text = dateEndoo

        submitBtn.setOnClickListener {
            sendReservation()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    fun sendReservation(){
        val url = "http://10.0.2.2:8080/reservation/add"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("idPlace",idPlace)
        rootObject.put("idCar",idCar)
        rootObject.put("dateBegin",dateBegin)
        rootObject.put("dateEnd",dateEnd)

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.i("Response code: ", response.code.toString())
            }

            override fun onFailure(call: Call, e: IOException) {
                print(e.printStackTrace())
            }
        })
    }
}