package com.example.parkIt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class AddCarActivity : AppCompatActivity() {
    private lateinit var brand: EditText;
    private lateinit var model: EditText;
    private lateinit var plate: EditText;

    private lateinit var jwtToken: String;
    private lateinit var username: String;

    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        brand = findViewById(R.id.editTextAddCarBrand)
        model = findViewById(R.id.editTextAddCarModel)
        plate = findViewById(R.id.editTextAddCarLicense)

        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Add new car"

        val sendNuke = findViewById<Button>(R.id.buttonAddCar);

        sendNuke.setOnClickListener {
            postAddNewCar()
        }

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("Key", "XD").toString()
        username = sharedPreferences.getString("SearchKey", "XD").toString()
    }

    private fun succesAdd(){
        Handler(Looper.getMainLooper()).post(Runnable {
            Toast.makeText(applicationContext, "car add!", Toast.LENGTH_SHORT).show()
        })
        val intent = Intent(this@AddCarActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun postAddNewCar() = GlobalScope.async {
        val username = username;
        val brand = brand.text;
        val model = model.text;
        val plate = plate.text;

        val url = "http://10.0.2.2:8080/addcar/$username"
        val client = OkHttpClient();
        val rootObject = JSONObject();
        rootObject.put("mark", brand.toString())
        rootObject.put("model", model.toString())
        rootObject.put("licencePlate", plate.toString())

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 201){
                    runOnUiThread {
                        succesAdd()
                    }
                }else if (response.code == 400){
                    runOnUiThread {
                        Toast.makeText(applicationContext, "incorrect value", Toast.LENGTH_SHORT).show()
                    }
                }
                Log.i("Response code:   ", response.code.toString())
            }

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "cannot connect with server", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}