package com.androidAssignment2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.androidAssignment2.util.UsersList

class ContactsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _contactList = savedStateHandle.getLiveData("contacts", UsersList.getUsers())

    val contactList: LiveData<List<Contact>> = _contactList


    fun getListUsers() = _contactList.value

    fun deleteContact(index: Int) {
        _contactList.value = _contactList.value?.toMutableList()?.apply {
            removeAt(index)
        }
    }

    fun deleteContact(contact: Contact) {
        _contactList.value = _contactList.value?.minus(contact)
    }

    fun addContact(contact: Contact) {
        _contactList.value = _contactList.value?.plus(contact)
    }
}