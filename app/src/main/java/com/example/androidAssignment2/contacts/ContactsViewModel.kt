package com.example.androidAssignment2.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {

    val contactList: MutableLiveData<List<Contact>> = MutableLiveData()

    init {
        contactList.value = UsersList.getUsers()
    }

    fun getListUsers() = contactList.value

    fun deleteContact(index: Int) {
        contactList.value=contactList.value?.toMutableList()?.apply {
            removeAt(index)
        }
    }
    fun deleteContact(contact: Contact) {
        contactList.value = contactList.value?.minus(contact)
    }

    fun addContact(contact: Contact) {
        contactList.value = contactList.value?.plus(contact)
    }
}