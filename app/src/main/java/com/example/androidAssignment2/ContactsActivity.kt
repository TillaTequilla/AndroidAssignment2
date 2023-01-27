package com.example.androidAssignment2


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidAssignment2.adapter.ContactsRecycleViewAdapter
import com.example.androidAssignment2.databinding.ActivityContactsBinding
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidAssignment2.adapter.ContactController
import com.example.androidAssignment2.architecture.BaseActivity
import com.example.androidAssignment2.contacts.Contact
import com.example.androidAssignment2.contacts.ContactsViewModel
import com.example.androidAssignment2.util.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar

class ContactsActivity : BaseActivity<ActivityContactsBinding>(ActivityContactsBinding::inflate),
    ContactController {


    private val contactViewModel: ContactsViewModel by viewModels()
    private val adapter: ContactsRecycleViewAdapter by lazy {
        ContactsRecycleViewAdapter(contactController = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val holder = viewHolder as ContactsRecycleViewAdapter.Holder
                holder.binding.IvRemoveContact.performClick()
            }
        }


        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        binding.tvAddContact.setOnClickListener {
            val dialog = DialogFragmentAddContact()
            dialog.show(supportFragmentManager, "addContact")
        }

        contactViewModel.contactList.observe(this) {
            adapter.submitList(contactViewModel.contactList.value)
        }
    }

    fun addContact(contact: Contact) {
        contactViewModel.addContact(contact)
    }

    override fun deleteUser(contact: Contact, view: View) {
        contactViewModel.deleteContact(contact)
        undoUserDeletion(view, contact)
    }

    private fun undoUserDeletion(view: View, contact: Contact?) {
        val delMessage = Snackbar.make(
            view,
            getString(R.string.contacts_sbRemoved, contact!!.name),
            Snackbar.LENGTH_LONG
        )
        delMessage.setAction("Cancel") {
            contactViewModel.addContact(contact)
        }.show()
    }


}