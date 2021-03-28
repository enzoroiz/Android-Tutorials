package com.enzoroiz.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_data_item.view.*

class RecyclerViewAdapter(private val list: List<Team>, private val onSelectedItem: (Team) -> Unit): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_data_item, parent, false)
        return ViewHolder(listItemView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onSelectedItem)
    }
}

class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(team: Team, onSelectedItem: (Team) -> Unit) {
        view.txtItemText.text = "Team is ${team.name} - ${team.countryCode}"
        view.setOnClickListener { onSelectedItem(team) }
    }
}