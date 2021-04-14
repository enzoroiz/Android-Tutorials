package com.wundermobility.qatest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment() {
    companion object {
        private const val FOCUSED_MARKER_ZOOM_LEVEL = 16F
        const val NEARBY_VEHICLES_LIST = "nearby_vehicles_list"
    }

    private val viewModel by viewModels<MapFragmentViewModel>()
    private val callback = OnMapReadyCallback { googleMap ->
        viewModel.setupMarkers(googleMap)
        setupObservers()
        btnMapLocateVehicle.setOnClickListener {
            viewModel.locateVehicle(
                googleMap,
                FOCUSED_MARKER_ZOOM_LEVEL
            )
        }
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

        btnMapNearbyVehicles.setOnClickListener {
            val intent = Intent(requireContext(), NearbyVehiclesListActivity::class.java).apply {
                putExtra(NEARBY_VEHICLES_LIST, viewModel.vehicles)
            }

            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.selectedVehicleLiveData.observe(this, Observer { vehicle ->
            vehicle?.let { showVehicleAvailableFragment(vehicle) }
        })

//        viewModel.rentedVehicleLiveData.observe(this, Observer { vehicle ->
//
//        })
    }

    private fun showVehicleAvailableFragment(vehicle: Vehicle) {
//        if (vehicle == viewModel.selectedVehicleLiveData.value) return

        val fragment = VehicleAvailableFragment.newInstance(vehicle)
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
            replace(R.id.fragmentMapCardContainer, fragment, null)
            commit()
        }
    }
}