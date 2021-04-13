package com.wundermobility.qatest

import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng

data class Vehicle(
    val id: Long,
    val name: String,
    val description: String,
    val position: LatLng,
    val type: String,
    val fuelLevel: Int,
    val pricePerMinute: Double,
    @DrawableRes val image: Int,
    @DrawableRes val markerIcon: Int
)