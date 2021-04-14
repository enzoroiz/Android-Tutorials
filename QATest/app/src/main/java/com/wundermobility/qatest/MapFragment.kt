package com.wundermobility.qatest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {
    companion object {
        const val NEARBY_VEHICLES_LIST = "nearby_vehicles_list"
        private const val VEHICLE_INTERACTION_CARD_FRAGMENT_TAG = "vehicle_interaction_card_fragment"
    }

    private val viewModel by activityViewModels<MapFragmentViewModel>()
    private val callback = OnMapReadyCallback { googleMap ->
        viewModel.googleMap = googleMap
        viewModel.setupMarkers()
        setupObservers()
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

        btnMapLocateVehicle.isEnabled = false
        btnMapNearbyVehicles.setOnClickListener {
            val intent = Intent(requireContext(), NearbyVehiclesListActivity::class.java).apply {
                putExtra(NEARBY_VEHICLES_LIST, viewModel.vehicles)
            }

            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.selectedVehicleLiveData.observe(this, Observer { selectedVehicle ->
            selectedVehicle?.let {  vehicle ->
                updateVehicleInteractionFragment()
                btnMapLocateVehicle.apply {
                    isEnabled = true
                    setOnClickListener {
                        viewModel.locateVehicle(vehicle, MapFragmentViewModel.FOCUSED_MARKER_ZOOM_LEVEL)
                    }
                }
            } ?: let {
                btnMapLocateVehicle.isEnabled = false
                updateVehicleInteractionFragment()
            }
        })
    }

    private fun updateVehicleInteractionFragment() {
        val fragment = VehicleInteractionFragment.newInstance()
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down)
            replace(R.id.fragmentMapCardContainer, fragment, VEHICLE_INTERACTION_CARD_FRAGMENT_TAG)
            commit()
        }
    }
}