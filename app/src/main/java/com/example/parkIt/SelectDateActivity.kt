package com.example.parkIt

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_date.*
import kotlinx.android.synthetic.main.main_navbar.*
import java.util.*
import kotlin.collections.ArrayList


class SelectDateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

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

        navBar.text = "Nazwa Ulicy" //TODO zmieniac po klikniecu na mapie
        val carSpinner: Spinner = carSpinner
        val exampleList = generateDummyList()
        val carAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.car_spinner_item,
            R.id.text_spinner_item,
            exampleList //TODO lista wchodzaca do rozwijanej listy
        )

        btnDateFrom.setOnClickListener {
            btnPressed = 0
            showDialog(DATE_DIALOG_ID);
        }
        btnDateTo.setOnClickListener {
            btnPressed = 1
            showDialog(DATE_DIALOG_ID);
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

    private fun generateDummyList(): List<String> {
        val list = ArrayList<String>()
        list += "Fiat" + " Multipla"
        list += "Citroen" + " Berlingo"
        list += "S0512341241"
        return list
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
            .append(mDay).append("/")
            .append(mMonth + 1).append("/")
            .append(mYear).append(" ")
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