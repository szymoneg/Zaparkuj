package com.example.parkIt

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.parkIt.data.Parkings
import com.google.gson.Gson
import okhttp3.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var username: TextView
    private lateinit var jwtToken: String;
    private var arrayList = ArrayList<OverlayItem>()
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

        getParkings()
        Thread.sleep(2000)

        val anotherItemizedIconOverlay = ItemizedIconOverlay<OverlayItem>(
            this, arrayList, object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    Log.i("XDD","Działa "+item?.snippet)
                    return false
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            },
        )
        map?.getOverlays()?.add(anotherItemizedIconOverlay)


        username = findViewById(R.id.user_name_drawer)
        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username.text = sharedPreferences.getString("SearchKey", "XD").toString()
        jwtToken = sharedPreferences.getString("Key", "XD").toString()

        drawerLayout = findViewById(R.id.drawer_layout)
    }

    fun getParkings() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/parkings")
            //.addHeader("Authorization", "Bearer $jwtToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            // TODO: 20.12.2020 regex to do
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }
                    if (response.code == 200) {
                        val datajson = response.body?.string();
                        val gson = Gson();
                        val enums: Array<Parkings> = gson.fromJson(
                            datajson,
                            Array<Parkings>::class.java
                        )
                        enums.map { data ->
                            arrayList.add(
                                OverlayItem(
                                    data.idParking.toString(),
                                    data.address,
                                    GeoPoint(data.latitude, data.longitude)
                                )
                            )
                        }
                        runOnUiThread {
                            Log.i("XDD", enums.get(1).address)
                        }
                    } else {
                        Log.e("----Edit:", response.code.toString())
                    }

                    Log.i("Value", "XDDD");
                }
            }
        })
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


    public fun clickMenu(view: View) {
        openDrawer(drawerLayout)
    }

    private fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    public fun clickBack(view: View) {
        closeDrawer(drawerLayout)
    }

    private fun closeDrawer(drawerLayout: DrawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
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
