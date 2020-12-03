package com.example.parkIt

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class RegisterActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById(R.id.editTextRegEmail)
        password = findViewById(R.id.editTextRegPassword)

        val button = findViewById<Button>(R.id.buttonCreate)
        button.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val email = email.text
        val password = password.text
        val url = "http://10.0.2.2:8080/register"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("username", "Test")
        rootObject.put("password", password.toString())
        rootObject.put("email", email.toString())

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.i("Response code: ",response.code.toString())
                hideKey()
            }

            override fun onFailure(call: Call, e: IOException) {
                print(e.printStackTrace())
            }
        })

    }

    private fun hideKey() {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    this.currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}