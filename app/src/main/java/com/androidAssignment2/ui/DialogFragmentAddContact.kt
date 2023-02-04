package com.androidAssignment2.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.androidAssignment2.databinding.AddContactBinding
import com.example.androidAssignment2.R
import com.androidAssignment2.extension.setSizePercent


class DialogFragmentAddContact : DialogFragment() {

    private var imageUri: Uri? = null
    private lateinit var binding: AddContactBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddContactBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSizePercent(85, 90)
        listenerInitialization()

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    imageUri = result.data?.data
                    binding.ivAddContactPhoto.setImageURI(imageUri)
                }
            }
    }

    private fun listenerInitialization() {
        binding.run {
            ivAddContactChoosePhoto.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                launcher.launch(intent)
            }

            btnSaveContact.setOnClickListener {
                if (etUsernameNew.text!!.isEmpty()) {
                    Toast.makeText(
                        context,
                        getString(R.string.contacts_toast_noInformation),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val contact = createContact()
                    addContactToActivity(contact)
                    dismiss()
                }
            }
        }

    }

    private fun addContactToActivity(contact: Contact) {
        activity?.let {
            (it as ContactsActivity).apply {
                addContact(contact)
            }
        }
    }

    private fun createContact(): Contact {
        binding.run {
            return Contact(
                imageUri.toString(),
                etUsernameNew.text.toString(),
                etCareerNew.text.toString()
            )
        }
    }

}