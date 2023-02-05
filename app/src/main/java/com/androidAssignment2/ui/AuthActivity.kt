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
import com.androidAssignment2.util.PreferenceHelper
import com.example.androidAssignment2.R

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    private val authActivityViewModel: AuthActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        authActivityViewModel.sharedPreferences = PreferenceHelper.init(this)
        super.onCreate(savedInstanceState)
        getPreferencesData()
        listenerInitialization()
    }

    private fun listenerInitialization() {
        with(binding) {
            etPassword.doAfterTextChanged { text ->
                if (text!!.length < 5) {
                    tilPassword.error = getString(R.string.login_error_password_few_symbols)
                } else if (!text.contains("\\d".toRegex())) {
                    tilPassword.error = getString(R.string.login_error_password_number)
                } else tilPassword.error = null
            }

            etEmail.doAfterTextChanged { text ->
                if (NameParser.validEmail(text.toString())) {
                    tilEmail.error = null
                } else tilEmail.error = getString(R.string.login_error_email_valid_email)
            }

            btnRegister.setOnClickListener {
                if (cbRememberMe.isChecked) {
                    rememberInformation()
                } else authActivityViewModel.sharedPreferences.edit().clear().apply()
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
                    && etEmail.text!!.isNotEmpty() && etPassword.text!!.isNotEmpty()
        }
    }

    private fun getPreferencesData() {
        with(binding) {
            if (
                PreferenceHelper.getValueFromSharedPreferences(
                    Constance.SHARED_PREFERENCES_REMEMBER,
                    false
                )
            ) {
                etEmail.setText(
                    PreferenceHelper.getValueFromSharedPreferences(
                        Constance.SHARED_PREFERENCES_EMAIL,
                        ""
                    )
                )
                etPassword.setText(
                    PreferenceHelper.getValueFromSharedPreferences(
                        Constance.SHARED_PREFERENCES_PASSWORD,
                        ""
                    )
                )
                cbRememberMe.isChecked = true
            }

        }
    }

    private fun rememberInformation() {
        val checked = binding.cbRememberMe.isChecked
        authActivityViewModel.apply {
            PreferenceHelper.putValueToSharedPreferences(
                Constance.SHARED_PREFERENCES_EMAIL,
                binding.etEmail.text.toString()
            )
            PreferenceHelper.putValueToSharedPreferences(
                Constance.SHARED_PREFERENCES_PASSWORD,
                binding.etPassword.text.toString()
            )
            PreferenceHelper.putValueToSharedPreferences(
                Constance.SHARED_PREFERENCES_REMEMBER,
                checked
            )
        }

    }

    private fun getName(): String {
        return NameParser.getName(binding.etEmail.text.toString())
    }


}