package com.example.parkIt.web

import android.util.Log
import com.example.parkIt.data.CarItem
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ConnectionAPI {
    fun deleteReservation(idReservation: Int){
        val url = "http://10.0.2.2:8080/deletereservation/$idReservation"
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .delete()
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

    fun deleteCar(idCar: Int){
        val url = "http://10.0.2.2:8080/deletecar/$idCar"
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .delete()
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