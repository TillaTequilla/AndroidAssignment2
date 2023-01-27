package com.example.androidAssignment2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.example.androidAssignment2.architecture.BaseActivity
import com.example.androidAssignment2.constance.Constance
import com.example.androidAssignment2.databinding.ActivityAuthBinding
import com.example.androidAssignment2.util.NameParser

class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    private val authActivityViewModel: AuthActivityViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        authActivityViewModel.sharedPreferences =
            getSharedPreferences(Constance.SHAREDPREFERENCES_NAME, MODE_PRIVATE)
        sharedPreferences = authActivityViewModel.sharedPreferences
        super.onCreate(savedInstanceState)
        getPreferencesData()
        listenerInitialization()
    }

    private fun listenerInitialization() {
        with(binding) {
            tietPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.length < 5) {
                    tilPassword.error = getString(R.string.login_error_password_few_symbols)
                } else if (!text.contains("[0-9]".toRegex())) {
                    tilPassword.error = getString(R.string.login_error_password_number)
                } else tilPassword.error = null
            }

            tietEmail.doOnTextChanged { text, start, before, count ->
                if (!text!!.contains(".+\\..+@+[A-Za-z]+\\.[A-Za-z]+".toRegex())
                    || text.contains(" ")
                ) {
                    tilEmail.error = getString(R.string.login_error_email_valid_email)
                } else tilEmail.error = null
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
        if (sharedPreferences.getBoolean(Constance.SHAREDPREFERENCES_REMEMBER, false)) {
            binding.tietEmail.setText(
                sharedPreferences.getString(
                    Constance.SHAREDPREFERENCES_EMAIL,
                    null
                )
            )
            binding.tietPassword.setText(
                sharedPreferences.getString(
                    Constance.SHAREDPREFERENCES_PASSWORD,
                    null
                )
            )
            binding.cbRememberMe.isChecked = true
        }
    }

    private fun rememberInformation() {
        val checked = binding.cbRememberMe.isChecked
        val editor = sharedPreferences.edit()
        editor.putString(Constance.SHAREDPREFERENCES_EMAIL, binding.tietEmail.text.toString())
        editor.putString(Constance.SHAREDPREFERENCES_PASSWORD, binding.tietPassword.text.toString())
        editor.putBoolean(Constance.SHAREDPREFERENCES_REMEMBER, checked)
        editor.apply()
    }

    private fun getName(): String {
        return NameParser.getName(binding.tietEmail.text.toString())
    }


}