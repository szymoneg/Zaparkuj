package com.example.parkIt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.parkIt.data.SpotItem
import kotlinx.android.synthetic.main.spots_recycler.*

class SelectSpotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spots_recycler)
        val exampleList = generateDummyList()
        recycle_spot.adapter = SpotsAdapter(exampleList)
        recycle_spot.layoutManager = GridLayoutManager(this, 2)
        recycle_spot.setHasFixedSize(true)
    }

    private fun generateDummyList(): List<SpotItem> {
        val list = ArrayList<SpotItem>()
        list += SpotItem("01", true)
        list += SpotItem("02", true)
        list += SpotItem("03", false)
        list += SpotItem("04", false)
        list += SpotItem("05", true)
        return list
    }
}