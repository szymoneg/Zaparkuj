package com.example.parkIt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class RegisterActivity : AppCompatActivity() {
    private lateinit var username: EditText;
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var password2: EditText
    private lateinit var agree: CheckBox
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById(R.id.editTextRegEmail)
        username = findViewById(R.id.editTextRegUser)
        password = findViewById(R.id.editTextRegPassword)
        password2 = findViewById(R.id.editTextRegPassword2)
        agree = findViewById(R.id.checkBoxAgree)

        val button = findViewById<Button>(R.id.buttonCreate)
        val logLabel = findViewById<TextView>(R.id.textViewLogIn)

        button.setOnClickListener {
            sendMessage()
        }

        logLabel.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkRegValues(): Boolean {
        val reg = Regexes()
        if (agree.isChecked) {
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
        } else {
            agree.error = "Accept the ToS"
            return false;
        }
    }

    private fun sendMessage() {
        if (checkRegValues()) {
            val email = email.text
            val password = password.text
            val username = username.text
            val url = "http://10.0.2.2:8080/register"
            val client = OkHttpClient()
            val rootObject = JSONObject()
            rootObject.put("password", password.toString())
            rootObject.put("email", email.toString())
            rootObject.put("username",username.toString())

            val body = rootObject.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    Log.i("Response code: ", response.code.toString())
                    hideKey()
                    goToLogin()
                }

                override fun onFailure(call: Call, e: IOException) {
                    print(e.printStackTrace())
                }
            })
        } else {
            Log.e("---Regex", "Wrong values")
        }
    }

    private fun goToLogin() {
        Handler(Looper.getMainLooper()).post(Runnable {
            Toast.makeText(applicationContext, "account crated!", Toast.LENGTH_SHORT).show()
        })
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
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

    fun clickLog(view: View) {}
}