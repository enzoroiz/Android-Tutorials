package com.wundermobility.qatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_nearby_vehicles_list.*

class NearbyVehiclesListActivity : AppCompatActivity() {
    private val vehicles = mutableListOf<Vehicle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_vehicles_list)

        intent.getParcelableArrayListExtra<Vehicle>(MapFragment.NEARBY_VEHICLES_LIST)?.let {
            vehicles.addAll(it)
        }

        lstNearbyVehicles.layoutManager = LinearLayoutManager(this)
        lstNearbyVehicles.adapter = NearbyVehiclesListAdapter(vehicles) { vehicle ->
            VehicleDetailsDialogFragment().show(supportFragmentManager, vehicle)
        }
    }
}