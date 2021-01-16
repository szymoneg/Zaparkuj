package com.example.parkIt.data

import java.security.Timestamp
import java.time.LocalDate
import java.util.*

data class ReservationItem(
    val idReservation: Int,
    val placeName: String,
    val carMark: String,
    val licencePlate: String,
    val parkingAddress: String,
    val dateBegin: Date,
    val dateEnd: Date,
    val status: Boolean
)
