package com.androidAssignment2.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class AuthActivityViewModel : ViewModel() {
    lateinit var sharedPreferences: SharedPreferences

    fun putString(key:String, value: String){
        sharedPreferences.edit().putString(key,value).apply()
    }
}