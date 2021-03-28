package com.enzoroiz.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.enzoroiz.roomdatabase.database.Subscriber
import com.enzoroiz.roomdatabase.databinding.ListItemBinding

class SubscriberRecyclerViewAdapter(
    private val onClickListener: (Subscriber) -> Unit = { }
) : RecyclerView.Adapter<SubscriberViewHolder>() {
    private val subscribers = mutableListOf<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        return SubscriberViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = subscribers.size

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribers[position], onClickListener)
    }

    fun updateSubscribers(updatedSubscribers: List<Subscriber>) {
        subscribers.clear()
        subscribers.addAll(updatedSubscribers)
        notifyDataSetChanged()
    }
}

class SubscriberViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, onClickListener: (Subscriber) -> Unit) {
        binding.txtName.text = subscriber.name
        binding.txtEmail.text = subscriber.email
        binding.cardViewDetails.setOnClickListener { onClickListener(subscriber) }
    }
}