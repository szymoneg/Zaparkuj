package com.example.parkIt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import com.example.parkIt.utilities.Regexes

class ResetActivity : AppCompatActivity() {
    val reg = Regexes()
    private lateinit var username: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        val buttonReset = findViewById<Button>(R.id.buttonReset)
        username = findViewById(R.id.editTextResetUsername)

        buttonReset.setOnClickListener {
            sendResetPassword()
        }
    }


    fun sendResetPassword() {
        if (reg.checkUsername(username.text.toString())) {
            val uname = username.text.toString()
            val url = "http://10.0.2.2:8080/sendmail/$uname"
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .build()


            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    Log.i("Response code: ", response.code.toString())
                    Handler(Looper.getMainLooper()).post(Runnable {
                        Toast.makeText(applicationContext, "password change!", Toast.LENGTH_SHORT).show()
                    })
                    val intent = Intent(this@ResetActivity, LoginActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call, e: IOException) {
                    print(e.printStackTrace())
                }
            })
        } else {
            username.error = "Invalid username"
            Log.e("---Error: ", "Regex error")
        }
    }
}