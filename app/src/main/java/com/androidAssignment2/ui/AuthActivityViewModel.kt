package com.androidAssignment2.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.androidAssignment2.util.Constance
import com.androidAssignment2.util.PreferenceHelper


class AuthActivityViewModel : ViewModel() {
    fun putData(toString: String, toString1: String, checked: Boolean) {
        PreferenceHelper.putValueToSharedPreferences(
        Constance.SHARED_PREFERENCES_EMAIL,
            toString
    )
        PreferenceHelper.putValueToSharedPreferences(
            Constance.SHARED_PREFERENCES_PASSWORD,
            toString1
        )
        PreferenceHelper.putValueToSharedPreferences(
            Constance.SHARED_PREFERENCES_REMEMBER,
            checked
        )
    }

    lateinit var sharedPreferences: SharedPreferences


}