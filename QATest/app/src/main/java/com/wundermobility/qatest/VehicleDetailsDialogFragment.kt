package com.wundermobility.qatest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.nearby_vehicles_list_item.*

class VehicleDetailsDialogFragment: DialogFragment() {
    private lateinit var vehicle: Vehicle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_vehicle_details, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        imgNearbyVehicleImage.setImageResource(vehicle.image)
        txtNearbyVehicleListItemType.text = vehicle.type
        txtNearbyVehicleListItemName.text = vehicle.name
        txtNearbyVehicleListItemDescription.text = vehicle.description
        txtNearbyVehicleListItemPrice.text = vehicle.price
        txtNearbyVehicleListItemFuelLevel.text = vehicle.fuelLevel
    }

    fun show(fragmentManager: FragmentManager, selectedVehicle: Vehicle) {
        vehicle = selectedVehicle
        show(fragmentManager, null)
    }
}