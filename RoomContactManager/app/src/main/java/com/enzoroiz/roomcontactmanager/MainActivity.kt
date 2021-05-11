package com.enzoroiz.roomcontactmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enzoroiz.roomcontactmanager.database.Contact
import com.enzoroiz.roomcontactmanager.databinding.ActivityMainBinding
import com.enzoroiz.roomcontactmanager.viewmodel.ContactsViewModel
import com.enzoroiz.roomcontactmanager.viewmodel.ContactsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ContactsViewModelFactory

    private val viewModel by viewModels<ContactsViewModel> { viewModelFactory }
    private lateinit var binding: ActivityMainBinding
    private val adapter = ContactsAdapter()

    private val onSwipeCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val contact = adapter.differ.currentList[viewHolder.adapterPosition]
            viewModel.deleteContact(contact)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel.getAllContacts().observe(this, {
            adapter.differ.submitList(it)
        })

        viewModel.contactByIdLiveData.observe(this, {
            Toast.makeText(this, "You tapped the Contact ID: ${it.id} with name: ${it.name}", Toast.LENGTH_LONG).show()
        })

        ItemTouchHelper(onSwipeCallback).attachToRecyclerView(binding.lstContacts)
        binding.lstContacts.layoutManager = LinearLayoutManager(this)
        binding.lstContacts.adapter = adapter
        adapter.onItemClickedListener = {
            val updatedContact = if (it.name == "Enzo Roiz") {
                Contact(
                    id = it.id,
                    name = "Updated Name",
                    email = "updated_email@gmail.com"
                )
            } else {
                Contact(
                    id = it.id,
                    name = "Enzo Roiz",
                    email = "enzo.test@gmail.com"
                )
            }

            viewModel.updateContact(updatedContact)
        }

        adapter.onItemLongClickedListener = {
            viewModel.getContactById(it)
        }

        binding.btnAdd.setOnClickListener {
            viewModel.saveContact(
                Contact(
                    name = "Enzo Roiz",
                    email = "enzo.test@gmail.com"
                )
            )
        }

        viewModel.getAllContacts()
    }
}