package com.example.parkIt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class SubmitActivity : AppCompatActivity() {
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
    }

    fun sendReservation(){

    }
}