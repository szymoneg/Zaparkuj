package com.example.parkIt.data

data class ReservationItem(
    val idReservation: String,
    val address: String,
    val carBrand: String,
    val license: String,
    val dateEnd: String
)
