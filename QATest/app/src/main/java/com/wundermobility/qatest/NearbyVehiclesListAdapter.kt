package com.wundermobility.qatest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.nearby_vehicles_list_item.view.*

class NearbyVehiclesListAdapter(private val vehicles: List<Vehicle>, private val onItemClickListener: (Vehicle) -> Unit): RecyclerView.Adapter<NearbyVehiclesListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NearbyVehiclesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nearby_vehicles_list_item, parent, false)
        return NearbyVehiclesListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: NearbyVehiclesListViewHolder, position: Int) {
        holder.bind(vehicles[position], onItemClickListener)
    }
}

class NearbyVehiclesListViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(vehicle: Vehicle, onItemClickListener: (Vehicle) -> Unit) {
        containerView.setOnClickListener { onItemClickListener(vehicle) }
        containerView.imgNearbyVehicleImage.setImageResource(vehicle.image)
        containerView.txtNearbyVehicleListItemType.text = vehicle.type
        containerView.txtNearbyVehicleListItemName.text = vehicle.name
        containerView.txtNearbyVehicleListItemDescription.text = vehicle.description
        containerView.txtNearbyVehicleListItemPrice.text = vehicle.price
        containerView.txtNearbyVehicleListItemFuelLevel.text = vehicle.fuelLevel
    }
}