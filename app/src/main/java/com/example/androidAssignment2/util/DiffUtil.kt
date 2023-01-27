package com.example.androidAssignment2.util

import androidx.recyclerview.widget.DiffUtil
import com.example.androidAssignment2.contacts.Contact

object DiffUtil : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return  oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}
