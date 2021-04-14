package com.wundermobility.qatest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_vehicle_available.*

private const val SELECTED_VEHICLE = "selected_vehicle"

class VehicleAvailableFragment : Fragment() {
    private var vehicle: Vehicle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vehicle = it.getParcelable(SELECTED_VEHICLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle_available, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vehicle?.let {
            imgCardVehicleAvailableImage.setImageResource(it.image)
            txtCardVehicleAvailableType.text = it.type
            txtCardVehicleAvailableName.text = it.name
            txtCardVehicleAvailableDescription.text = it.description
            txtCardVehicleAvailableFuelLevel.text = it.fuelLevel
            txtCardVehicleAvailablePrice.text = it.price
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(vehicle: Vehicle) =
            VehicleAvailableFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_VEHICLE, vehicle)
                }
            }
    }
}