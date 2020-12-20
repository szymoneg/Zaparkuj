package com.example.parkIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AddCarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        val navBar = findViewById<TextView>(R.id.action_bar_text);
        navBar.text = "Add new car"

    }
}