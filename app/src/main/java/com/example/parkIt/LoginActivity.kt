package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText;
    private lateinit var password: EditText;
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private lateinit var token: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.editTextLogEmail)
        password = findViewById(R.id.editTextLogPassword)

        val button = findViewById<Button>(R.id.buttonLogin)
        button.setOnClickListener {
            loggin()
            Thread.sleep(5000)
            goToMain()
        }
    }

    fun run() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/hello")
            .addHeader("Authorization", "Bearer $token")
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

                    Log.i("Value", "XDDD");
                }
            }
        })
    }

    private fun goToMain() {
        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        Handler(Looper.getMainLooper()).post(Runnable {
            Toast.makeText(applicationContext, "login succesful!", Toast.LENGTH_SHORT).show()
        })
        val value = token;
        val editor = sharedPreferences.edit()
        editor.putString("Key",value)
        editor.apply()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }


    private fun loggin() {
        val email = email.text;
        val password = password.text
        val url = "http://10.0.2.2:8080/login"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("email", email.toString())
        rootObject.put("password", password.toString())

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val json = Gson()
                    val value = json.fromJson(response.body?.string(), JwtTokenData::class.java)
                    //TODO zapisywanie value do sesji!
                    token = value.jwttoken;
                    Log.i("Response code: ", value.jwttoken)
                }else{
                    Log.e("Logowanie:  ", response.code.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                print(e.printStackTrace())
            }
        })
    }
}