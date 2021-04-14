package com.wundermobility.qatest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_vehicle_interaction.*

class VehicleInteractionFragment : Fragment() {
    private val viewModel by activityViewModels<MapFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.rentedVehicleLiveData.observe(viewLifecycleOwner, Observer { rentedVehicle ->
            setupActions(viewModel.selectedVehicleLiveData.value, rentedVehicle)
        })

        viewModel.selectedVehicleLiveData.value?.let { selected ->
            imgCardVehicleAvailableImage.setImageResource(selected.image)
            txtCardVehicleAvailableType.text = selected.type
            txtCardVehicleAvailableName.text = selected.name
            txtCardVehicleAvailableDescription.text = selected.description
            txtCardVehicleAvailableFuelLevel.text = selected.fuelLevel
            txtCardVehicleAvailablePrice.text = selected.price

            setupActions(selected, viewModel.rentedVehicleLiveData.value)
        }
    }

    private fun setupActions(selected: Vehicle?, rentedVehicle: Vehicle?) {
        btnCardVehicleAvailableGoToRentedVehicle.visibility = View.GONE
        btnCardVehicleAvailableRentVehicle.visibility = View.GONE
        btnCardVehicleAvailableEndRent.visibility = View.GONE

        rentedVehicle?.let { rented ->
            if (selected == rented) {
                btnCardVehicleAvailableEndRent.apply {
                    visibility = View.VISIBLE
                    setOnClickListener { viewModel.endRent() }
                }
            } else {
                btnCardVehicleAvailableGoToRentedVehicle.apply {
                    visibility = View.VISIBLE
                    setOnClickListener { viewModel.goToRentedVehicle() }
                }
            }
        } ?: let {
            btnCardVehicleAvailableRentVehicle.apply {
                visibility = View.VISIBLE
                setOnClickListener { viewModel.rentVehicle() }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = VehicleInteractionFragment()
    }
}