package com.wundermobility.qatest

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(
    val id: Long,
    val name: String,
    val description: String,
    val position: LatLng,
    val type: String,
    val fuelLevel: String,
    val price: String,
    @DrawableRes val image: Int,
    var rides: Int = 0
) : Parcelable