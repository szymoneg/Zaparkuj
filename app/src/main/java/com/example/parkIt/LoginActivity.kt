package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkIt.data.JwtTokenData
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText;
    private lateinit var password: EditText;
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private lateinit var token: String;
    private lateinit var searchKey: String;
    private lateinit var erorrText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.editTextLogUser)
        password = findViewById(R.id.editTextLogPassword)
        erorrText = findViewById(R.id.textViewErorr)

        val button = findViewById<Button>(R.id.buttonLogin)
        val regiseterLabel = findViewById<TextView>(R.id.textViewSignUp)

        button.setOnClickListener {
            loggin()
            Thread.sleep(2000)
        }

        regiseterLabel.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    @Override
    private fun goToMain() {
        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        Toast.makeText(applicationContext, "login succesful!", Toast.LENGTH_SHORT).show()
        val value = token;
        val editor = sharedPreferences.edit()
        editor.putString("Key",value)
        editor.putString("SearchKey",searchKey)
        editor.apply()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }


    private fun loggin() {
        val username = username.text;
        val password = password.text
        val url = "http://10.0.2.2:8080/login"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("username", username.toString())
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
                    token = value.jwttoken;
                    searchKey = value.username;
                    Log.i("Response code: ", searchKey)
                    runOnUiThread {
                        goToMain()
                    }
                }else{
                    runOnUiThread {
                        erorrText.visibility = View.VISIBLE;
                    }
                    Log.e("Logowanie:  ", response.code.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                print(e.printStackTrace())
            }
        })
    }
}