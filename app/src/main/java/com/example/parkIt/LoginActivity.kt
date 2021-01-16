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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import com.example.parkIt.utilities.Regexes


class LoginActivity : AppCompatActivity() {
    val reg = Regexes()
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
        val forgot = findViewById<TextView>(R.id.textViewForgot)

        button.setOnClickListener {
            loggin()
        }

        forgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetActivity::class.java)
            startActivity(intent)
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
        editor.putString("Key", value)
        editor.putString("SearchKey", searchKey)
        editor.apply()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finishAffinity()
        startActivity(intent)
    }


    private fun loggin() = GlobalScope.async {
        val url = "http://10.0.2.2:8080/login"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("username", username.text.toString())
        rootObject.put("password", password.text.toString())

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val json = Gson()
                    val value =
                        json.fromJson(response.body?.string(), JwtTokenData::class.java)
                    token = value.jwttoken;
                    searchKey = value.username;
                    Log.i("Response code: ", searchKey)
                    runOnUiThread {
                        goToMain()
                    }
                } else if (response.code == 401) {
                    token = "lololololo";
                    searchKey = "niedziała";
                    runOnUiThread {
                        Log.i("Response code: ", searchKey)
                        password.error = "Incorrect password"
                        username.error = "Incorrect username"
                    }
                } else {
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