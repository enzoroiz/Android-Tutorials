package com.wundermobility.qatest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {
    companion object {
        private const val MAP_ZOOM_LEVEL = 16F
    }

    private val vehicles = listOf(
        Vehicle(
            id = 1L,
            name = "KickScooter D1",
            description = "Fastest KickScooter available in the market.",
            position = LatLng(51.514656, 7.467411),
            type = "Kick Scooter",
            fuelLevel = 23,
            pricePerMinute = 1.45,
            image = R.drawable.ic_blue_motorcycle
        ),
        Vehicle(
            id = 2L,
            name = "Electric Bike B1",
            description = "Amazing Electric Bike!",
            position = LatLng(51.508818, 7.450705),
            type = "Electric Bike",
            fuelLevel = 84,
            pricePerMinute = 0.75,
            image = R.drawable.ic_orange_bike
        ),
        Vehicle(
            id = 3L,
            name = "Electric Car C1",
            description = "Power is there from the beginning!",
            position = LatLng(51.510760, 7.464505),
            type = "Electric Car",
            fuelLevel = 100,
            pricePerMinute = 12.75,
            image = R.drawable.ic_purple_car
        )
    )

    private val callback = OnMapReadyCallback { googleMap ->
        setupMarkers(googleMap)
        btnMapLocateVehicle.setOnClickListener { locateVehicle(googleMap) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setupMarkers(googleMap: GoogleMap) {
        vehicles.forEach { vehicle ->
            googleMap.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(vehicle.image))
                    .position(vehicle.position)
                    .title(vehicle.name)
                    .snippet(vehicle.description)
            ).apply { tag = vehicle }
        }

        locateVehicle(googleMap)
    }

    private fun locateVehicle(googleMap: GoogleMap) {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                vehicles.first().position,
                MAP_ZOOM_LEVEL
            )
        )
    }
}