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
    private lateinit var carList: Array<CarItem>;
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    fun getCars(username: String): Array<CarItem> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/cars/$username")
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
                        val enums: Array<CarItem> = gson.fromJson(
                            dataJson,
                            Array<CarItem>::class.java
                        )
                        carList = enums
                    } else {
                        Log.e("----Edit:", response.code.toString())
                    }

                    Log.i("Value", "XDDD");
                }
            }
        })
        return carList;
    }

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

}