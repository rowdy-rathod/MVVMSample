package com.example.mvvmsample.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveString(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun saveInt(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    fun getPreference(key: String, type: Int): Any? {
        // Type 1 is for String
        // Type 2 is for Int
        // Type 3 is for Boolean
        val result: Any
        try {
            when (type) {
                1 -> result = preference.getString(key, null)!!
                2 -> result = preference.getInt(key, -1)
                3 -> result = preference.getBoolean(key, false)
                else -> result = ""
            }
        } catch (e: Exception) {
            return null
        }
        return result
    }
}