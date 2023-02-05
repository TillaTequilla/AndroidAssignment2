package com.androidAssignment2.util


import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val APP_PREFERENCES = "app_preferences"
    lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context): SharedPreferences {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences
    }


    fun putValueToSharedPreferences(key: String, value: Any?) {
        when (value) {
            is String -> sharedPreferences.edit().putString(key, value).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
        }

    }

    inline fun <reified T : Any> getValueFromSharedPreferences(
        key: String,
        defaultValue: T? = null
    ): T =
        when (T::class) {
            String::class -> sharedPreferences.getString(key, defaultValue as String? ?: "") as T
            Boolean::class -> sharedPreferences.getBoolean(
                key,
                defaultValue as? Boolean ?: false
            ) as T
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
}