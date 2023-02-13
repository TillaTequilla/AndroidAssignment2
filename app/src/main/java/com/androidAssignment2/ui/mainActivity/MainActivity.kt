package com.androidAssignment2.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import com.androidAssignment2.architecture.BaseActivity
import com.androidAssignment2.util.Constance
import com.androidAssignment2.databinding.ActivityMainBinding
import com.androidAssignment2.ui.contactsActivity.ContactsActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvName.text = intent.getStringExtra(Constance.INTENT_NAME)

        binding.btnContacts.setOnClickListener {
            val intent = Intent(this@MainActivity, ContactsActivity::class.java)
            startActivity(intent)
        }
    }
}