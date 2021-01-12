package com.example.parkIt

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parkIt.data.CarItem
import com.example.parkIt.data.SectorItem
import com.example.parkIt.web.ConnectionAPI
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_select_date.*
import kotlinx.android.synthetic.main.main_navbar.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SelectDateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var username: String
    private lateinit var jwtToken: String
    private lateinit var address: String
    private lateinit var idParking: String

    private lateinit var arrCars: Array<CarItem>
    private lateinit var arrSectors: String
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    private lateinit var carSelected: String
    private lateinit var dateFrom: TextView
    private lateinit var timeFrom: TextView
    private lateinit var dateTo: TextView
    private lateinit var timeTo: TextView
    private var btnPressed: Int = 0

    private val TIME_DIALOG_ID = 1

    private val DATE_DIALOG_ID = 0

    private val cal: Calendar = Calendar.getInstance()
    private var mYear = cal.get(Calendar.YEAR)
    private var mMonth = cal.get(Calendar.MONTH)
    private var mDay = cal.get(Calendar.DAY_OF_MONTH)
    private var mHour = cal.get(Calendar.HOUR_OF_DAY)
    private var mMinute = cal.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date)

        val buttonCheck: Button = buttonCheck //TODO
        val btnDateFrom: Button = btnPickDateFrom
        val btnDateTo: Button = btnPickDateTo
        val navBar = action_bar_text
        dateTo = textViewDateTo
        timeTo = textViewTimeTo
        dateFrom = textViewDateFrom
        timeFrom = textViewTimeFrom

        val sharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("SearchKey", "XD").toString()
        jwtToken = sharedPreferences.getString("Key", "XD").toString()
        address = sharedPreferences.getString("address", "XD").toString()
        idParking = sharedPreferences.getString("parking", "XD").toString()

        val spinner = findViewById<Spinner>(R.id.carSpinner);

        //TODO sleep
        Thread.sleep(500)

        navBar.text = address;
        val carSpinner: Spinner = carSpinner
        getCars();
        Thread.sleep(1000)
        val carAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.car_spinner_item,
            R.id.text_spinner_item,
            arrCars.map { value ->
                value.idCar.toString() + ". " + value.mark + " " + value.model
            }
        )

        btnDateFrom.setOnClickListener {
            btnPressed = 0
            showDialog(DATE_DIALOG_ID);
        }
        btnDateTo.setOnClickListener {
            btnPressed = 1
            showDialog(DATE_DIALOG_ID);
        }

        buttonCheck.setOnClickListener {
            val dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
            val dateXD: String = "yyyy-MM-dd HH:mm"

            val sdf = SimpleDateFormat(dateFormat)
            Log.i("----OD: ", "${dateFrom.text} ${timeFrom.text}")
            Log.i("----DO: ", "${dateTo.text} ${timeTo.text}")
            sdf.applyPattern(dateXD);
            val dateBegin = sdf.parse("${dateFrom.text} ${timeFrom.text}");
            val dateEnd = sdf.parse("${dateTo.text} ${timeTo.text}");
            sdf.applyPattern(dateFormat);
            val dateStart = sdf.format(dateBegin);
            val dateEndoo = sdf.format(dateEnd);

            Log.i("----newOd", dateStart.toString());
            Log.i("----newDo", dateEndoo.toString());
            sendMessage(idParking.toInt(),dateStart,dateEndoo);

            //TODO sleep
            Thread.sleep(200);

            val json = Gson()
            val editor = sharedPreferences.edit()
            Log.i("lolo",arrSectors)
            editor.putString("Sectors", arrSectors)
            editor.putString("start",dateStart.toString())
            editor.putString("end",dateEndoo.toString())
            editor.putInt("car",arrCars[spinner.selectedItemId.toInt()].idCar.toInt())
            editor.putString("carName","${arrCars[spinner.selectedItemId.toInt()].mark} " +
                    "${arrCars[spinner.selectedItemId.toInt()].model} " +
                    "${arrCars[spinner.selectedItemId.toInt()].licencePlate} ")
            editor.apply()

            val intent = Intent(this@SelectDateActivity, SelectSectorActivity::class.java)
            startActivity(intent)
        }

        carSpinner.adapter = carAdapter
        carSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        carSelected = parent.getItemAtPosition(pos) as String
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        carSelected = parent.getItemAtPosition(0) as String
    }

    //TODO wyrzucić do innej klasy
    fun getCars() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/cars/$username")
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
                        Log.i("No działą! ", "XDDD")
                        val dataJson = response.body?.string();
                        val gson = Gson()
                        val enums: Array<CarItem> = gson.fromJson(
                            dataJson,
                            Array<CarItem>::class.java
                        )
                        runOnUiThread {
                            Log.i("XDD", enums.get(1).mark)
                        }
                        arrCars = enums
                    } else {
                        Log.e("----Edit:", response.code.toString())
                    }

                    Log.i("Value", "XDDD");
                }
            }
        })
    }

    fun sendMessage(idParking: Int, dateBegin: String, dateEnd: String) {
        val url = "http://10.0.2.2:8080/sector/countsector/$idParking"
        val client = OkHttpClient()
        val rootObject = JSONObject()
        rootObject.put("dateBegin", dateBegin)
        rootObject.put("dateEnd", dateEnd)

        val body = rootObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.i("Response code: ", response.code.toString())
                if (response.code.toString() == "200"){
                    val dataJson = response.body?.string();
                    val gson = Gson()
                    val enums = dataJson;
                    if (enums != null) {
                        arrSectors = enums
                    };
                    runOnUiThread {
                        Log.i("XDD", enums.toString())
                    }
                    //arrSectors = enums
                }else{
                    Log.e("Error", response.code.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                print(e.printStackTrace())
            }
        })
    }

    private fun updateTime() {
        val time = StringBuilder()
            .append(pad(mHour)).append(":")
            .append(pad(mMinute))
        if (btnPressed == 0) {
            timeFrom.text = time
        } else {
            timeTo.text = time
        }
    }

    private fun updateDate() {
        val date = java.lang.StringBuilder()
            .append(mYear).append("-")
            .append(mMonth + 1).append("-")
            .append(mDay).append("")
        if (btnPressed == 0) {
            dateFrom.text = date
        } else {
            dateTo.text = date
        }
        showDialog(TIME_DIALOG_ID)
    }

    private fun pad(c: Int): String {
        return if (c >= 10) c.toString() else "0$c"
    }

    private val mDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            mMonth = monthOfYear
            mYear = year
            mDay = dayOfMonth
            updateDate()
        }

    private val mTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            mHour = hourOfDay
            mMinute = minute
            updateTime()
        }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            DATE_DIALOG_ID -> return DatePickerDialog(
                this,
                mDateSetListener,
                mYear, mMonth, mDay
            )
            TIME_DIALOG_ID -> return TimePickerDialog(
                this,
                mTimeSetListener, mHour, mMinute, true
            )
        }
        return null
    }
}