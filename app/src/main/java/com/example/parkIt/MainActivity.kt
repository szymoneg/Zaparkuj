package com.example.parkIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        closeDrawer(drawerLayout)
    }
    fun clickLog(view: View) {
        print("Navigating to: logout")
        closeDrawer(drawerLayout)
    }
    fun clickSettings(view: View) {
        print("Navigating to: settings")
        closeDrawer(drawerLayout)
    }
    fun clickReserve(view: View) {
        print("Navigating to: reservations")
        closeDrawer(drawerLayout)
    }
    fun clickCars(view: View) {
        print("Navigating to: mycars")
        closeDrawer(drawerLayout)
    }
    fun clickHome(view: View) {
        print("Navigating to: home")
        closeDrawer(drawerLayout)
    }
}