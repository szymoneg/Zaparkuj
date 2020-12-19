package com.example.parkIt

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var preferences: SharedPreferences;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)

        Log.i("---Klucz:  ", sharedPreferences.getString("Key","XD").toString())
    }
}