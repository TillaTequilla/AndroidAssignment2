package com.androidAssignment2.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.androidAssignment2.architecture.BaseActivity
import com.androidAssignment2.util.Constance
import com.example.androidAssignment2.databinding.ActivityAuthBinding
import com.androidAssignment2.util.NameParser
import com.example.androidAssignment2.R

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    private val authActivityViewModel: AuthActivityViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        authActivityViewModel.sharedPreferences =
            getSharedPreferences(Constance.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        sharedPreferences = authActivityViewModel.sharedPreferences
        super.onCreate(savedInstanceState)
        getPreferencesData()
        listenerInitialization()
    }

    private fun listenerInitialization() {
        with(binding) {
            tietPassword.doAfterTextChanged { text ->
                if (text!!.length < 5) {
                    tilPassword.error = getString(R.string.login_error_password_few_symbols)
                } else if (!text.contains("\\d".toRegex())) {
                    tilPassword.error = getString(R.string.login_error_password_number)
                } else tilPassword.error = null
            }

            tietEmail.doAfterTextChanged { text ->
                if (NameParser.validEmail(text.toString())) {
                    tilEmail.error = null
                } else tilEmail.error = getString(R.string.login_error_email_valid_email)
            }

            bRegister.setOnClickListener {
                if (cbRememberMe.isChecked) {
                    rememberInformation()

                } else sharedPreferences.edit().clear().apply()
                if (checkForInput()) {
                    val name: String = getName()
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    intent.putExtra(Constance.INTENT_NAME, name)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                }
            }
        }
    }

    private fun checkForInput(): Boolean {
        with(binding) {
            return tilEmail.error == null && tilPassword.error == null
                    && tietEmail.text!!.isNotEmpty() && tietPassword.text!!.isNotEmpty()
        }
    }

    private fun getPreferencesData() {
        with(binding) {
            if (sharedPreferences.getBoolean(Constance.SHARED_PREFERENCES_REMEMBER, false)) {
                tietEmail.setText(
                    sharedPreferences.getString(
                        Constance.SHARED_PREFERENCES_EMAIL,
                        null
                    )
                )
                tietPassword.setText(
                    sharedPreferences.getString(
                        Constance.SHARED_PREFERENCES_PASSWORD,
                        null
                    )
                )
                cbRememberMe.isChecked = true
            }
        }
    }

    private fun rememberInformation() {
        val checked = binding.cbRememberMe.isChecked
        sharedPreferences.edit().apply {
            putString(Constance.SHARED_PREFERENCES_EMAIL, binding.tietEmail.text.toString())
            putString(Constance.SHARED_PREFERENCES_PASSWORD, binding.tietPassword.text.toString())
            putBoolean(Constance.SHARED_PREFERENCES_REMEMBER, checked)
            apply()
        }

    }

    private fun getName(): String {
        return NameParser.getName(binding.tietEmail.text.toString())
    }


}