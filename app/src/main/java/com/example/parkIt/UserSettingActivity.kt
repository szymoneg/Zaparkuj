package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


//TODO pole username
class UserSettingActivity : AppCompatActivity() {
    private lateinit var jwtToken: String;
    private lateinit var username: String;
    private lateinit var firstName: EditText;
    private lateinit var lastName: EditText;
    private lateinit var mail: EditText;
    private lateinit var oldPassword: EditText;
    private lateinit var password: EditText;
    private lateinit var password2: EditText;
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)

        mail = findViewById(R.id.editTextSettingsEmail)
        firstName = findViewById(R.id.editTextSettingsName)
        lastName = findViewById(R.id.editTextSettingsSurname)
        oldPassword = findViewById(R.id.editTextSettingsPrevPassword)
        password = findViewById(R.id.editTextSettingsNewPassword)
        password2 = findViewById(R.id.editTextSettingsConfirmPassword)

        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Settings"


        val sendNudes = findViewById<Button>(R.id.buttonSetData)
        val sendPassword = findViewById<Button>(R.id.buttonSetPassword)

        sendPassword.setOnClickListener {
            postChangePassword()
        }

        sendNudes.setOnClickListener {
            postUserData()
        }


        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("SearchKey", "XD").toString()
        jwtToken = sharedPreferences.getString("Key", "XD").toString()
        mail.setText(sharedPreferences.getString("SearchKey", "XD").toString());
        getUserData();

        //Log.i("---Klucz:  ", sharedPreferences.getString("SearchKey","XD").toString())
    }

    fun getUserData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/edit/$username")
            .addHeader("Authorization", "Bearer $jwtToken")
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
                        val json = Gson()
                        val value = json.fromJson(response.body?.string(), Map::class.java);
                        runOnUiThread {
                            firstName.setText(value.get("firstname").toString())
                            lastName.setText(value.get("lastname").toString())
                        }
                    } else {
                        Log.e("----Edit:", response.code.toString())
                    }

                    Log.i("Value", "XDDD");
                }
            }
        })
    }


    //TODO zmiana hasla
    fun postChangePassword(){
        val oldPassword = oldPassword
        val newPassword = password
    }

    fun postUserData(){
        val email = mail.text;
        val firstname = firstName.text;
        val lastname = lastName.text;
        val url = "http://10.0.2.2:8080/edit/$email"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("email", email.toString())
        rootObject.put("firstname", firstname.toString())
        rootObject.put("lastname",lastname.toString())

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                Log.i("Response code:   ",response.code.toString())
                runOnUiThread {
                    val intent = Intent(this@UserSettingActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }
        })

    }
}