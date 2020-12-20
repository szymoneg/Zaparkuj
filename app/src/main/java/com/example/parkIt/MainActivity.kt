package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var username: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        username = findViewById(R.id.user_name_drawer)
        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username.text = sharedPreferences.getString("SearchKey","XD").toString()
        //Log.i("---Klucz:  ", sharedPreferences.getString("Key","XD").toString())

        drawerLayout = findViewById(R.id.drawer_layout)
    }
    public fun clickMenu (view: View){
        openDrawer(drawerLayout)
    }

    private fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }
    public fun clickBack(view: View){
        closeDrawer(drawerLayout)
    }
    private fun closeDrawer(drawerLayout: DrawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun clickAbout(view: View) {
        print("Navigating to: about us")
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("http://tgryl.pl/")
        startActivity(openURL)
        closeDrawer(drawerLayout)
    }
    fun clickLog(view: View) {
        print("Navigating to: logout")
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        closeDrawer(drawerLayout)
    }
    fun clickSettings(view: View) {
        print("Navigating to: settings")
        val intent = Intent(this@MainActivity, UserSettingActivity::class.java)
        startActivity(intent)
        closeDrawer(drawerLayout)
    }
    fun clickReserve(view: View) {
        print("Navigating to: reservations")
        closeDrawer(drawerLayout)
    }
    fun clickCars(view: View) {
        print("Navigating to: mycars")
        val intent = Intent(this@MainActivity, AddCar::class.java)
        startActivity(intent)
        closeDrawer(drawerLayout)
    }
    fun clickHome(view: View) {
        print("Navigating to: home")
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(intent)
        closeDrawer(drawerLayout)
    }
}