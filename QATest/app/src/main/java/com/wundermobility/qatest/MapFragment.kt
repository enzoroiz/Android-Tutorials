package com.wundermobility.qatest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        btnMapLocateVehicle.setOnClickListener { viewModel.locateVehicle(googleMap, FOCUSED_MARKER_ZOOM_LEVEL) }
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
}