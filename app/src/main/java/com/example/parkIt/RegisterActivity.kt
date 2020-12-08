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
    private lateinit var password2: EditText
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById(R.id.editTextRegEmail)
        password = findViewById(R.id.editTextRegPassword)
        password2 = findViewById(R.id.editTextRegPassword2)

        val button = findViewById<Button>(R.id.buttonCreate)
        button.setOnClickListener {
            sendMessage()
        }
    }

    private fun checkRegValues(): Boolean {
        val reg = Regexes()
        if (reg.checkMail(email.text.toString())) {
            return if (password.text.toString() == password2.text.toString()) {
                if (reg.checkPassword(password.text.toString())) {
                    true;
                } else {
                    password.error = "Password doesn't meet the requirements: Length: 8-32. Allowed special chars: ?.*@\\$!%#&"
                    false;
                }
            } else {
                password2.error = "Passwords don't match";
                false;
            }
        } else {
            email.error = "Incorrect email!"
            return false;
        }
    }

    private fun sendMessage() {
        if (checkRegValues()) {
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
                    Log.i("Response code: ", response.code.toString())
                    hideKey()
                }

                override fun onFailure(call: Call, e: IOException) {
                    print(e.printStackTrace())
                }
            })
        } else {
            Log.e("---Regex", "Wrong values")
        }
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