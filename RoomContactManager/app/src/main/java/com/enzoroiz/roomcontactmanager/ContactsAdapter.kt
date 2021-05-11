package com.enzoroiz.roomcontactmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enzoroiz.roomcontactmanager.database.Contact
import com.enzoroiz.roomcontactmanager.databinding.ListItemBinding

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    var onItemClickedListener = { _: Contact -> }
    var onItemLongClickedListener = { _: Long -> }

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ContactsViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.txtName.text = contact.name
            binding.txtEmail.text = contact.email
            binding.root.setOnClickListener { onItemClickedListener(contact) }
            binding.root.setOnLongClickListener {
                onItemLongClickedListener(contact.id!!)
                true
            }
        }
    }
}