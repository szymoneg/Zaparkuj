package com.example.parkIt.data

data class SectorItem(
    val idSector: Int,
    val sectorName: String,
    val freePlaces: Int,
    val occupatePlaces: Int,
    val price: Double
)
