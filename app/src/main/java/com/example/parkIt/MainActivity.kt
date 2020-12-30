package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var username: TextView
    private var map: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        map = findViewById(R.id.map);
        map?.setTileSource(TileSourceFactory.MAPNIK)

        map?.setBuiltInZoomControls(true);
        map?.setMultiTouchControls(true);

        val mapController = map?.getController()
        mapController?.setZoom(17)
        val startPoint = GeoPoint(50.02009, 20.99191)
        mapController?.setCenter(startPoint)

        val startPoint1 = GeoPoint(50.0195, 20.99290)
        val startMarker = Marker(map)
        startMarker.position = startPoint1
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map?.getOverlays()?.add(startMarker)
        startMarker.setOnMarkerClickListener { marker, mapView ->
            Log.i("XDD","XDD")
            true
        }

        username = findViewById(R.id.user_name_drawer)
        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username.text = sharedPreferences.getString("SearchKey", "XD").toString()
        //Log.i("---Klucz:  ", sharedPreferences.getString("Key","XD").toString())

        drawerLayout = findViewById(R.id.drawer_layout)
    }

    fun showXD(){
        Log.i("XDDD", "XDDD")
    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map!!.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map!!.onPause() //needed for compass, my location overlays, v6.0.0 and up
    }


    public fun clickMenu(view: View){
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
        val intent = Intent(this@MainActivity, SelectCarActivity::class.java)
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
