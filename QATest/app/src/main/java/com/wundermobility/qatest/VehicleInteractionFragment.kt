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
            progressBar.visibility = View.GONE
            setupActions(viewModel.selectedVehicleLiveData.value, rentedVehicle)
        })

        viewModel.selectedVehicleLiveData.value?.let { selected ->
            btnCardVehicleClose.setOnClickListener { viewModel.deselectVehicle() }
            imgCardVehicleImage.setImageResource(selected.image)
            txtCardVehicleType.text = selected.type
            txtCardVehicleName.text = selected.name
            txtCardVehicleDescription.text = selected.description
            txtCardVehicleFuelLevel.text = selected.fuelLevel
            txtCardVehiclePrice.text = selected.price

            setupActions(selected, viewModel.rentedVehicleLiveData.value)
        } ?: let { layoutCardVehicle.visibility = View.INVISIBLE }
    }

    private fun setupActions(selectedVehicle: Vehicle?, rentedVehicle: Vehicle?) {
        btnCardVehicleGoToRentedVehicle.visibility = View.GONE
        btnCardVehicleRentVehicle.visibility = View.GONE
        btnCardVehicleEndRent.visibility = View.GONE

        selectedVehicle?.let { selected ->
            if (selected.rides > 0) {
                txtCardVehicleRides.text =
                    getString(R.string.button_card_vehicle_rides_text, selected.rides)
                txtCardVehicleRides.visibility = View.VISIBLE
            }
        }

        rentedVehicle?.let { rented ->
            if (selectedVehicle == rented) {
                btnCardVehicleEndRent.apply {
                    visibility = View.VISIBLE
                    isEnabled = true

                    setOnClickListener {
                        isEnabled = false
                        progressBar.visibility = View.VISIBLE
                        viewModel.endRent()
                    }
                }
            } else {
                btnCardVehicleGoToRentedVehicle.apply {
                    visibility = View.VISIBLE
                    setOnClickListener { viewModel.goToRentedVehicle() }
                }
            }
        } ?: let {
            btnCardVehicleRentVehicle.apply {
                visibility = View.VISIBLE
                isEnabled = true

                setOnClickListener {
                    isEnabled = false
                    progressBar.visibility = View.VISIBLE
                    viewModel.rentVehicle()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = VehicleInteractionFragment()
    }
}