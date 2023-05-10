package com.example.storyapp.helper

import android.content.Context

internal class AuthPreference(context: Context) {
    companion object {
        const val USER_PREF = "USER_PREF"
    }

    var sharedPreferences = context.getSharedPreferences(USER_PREF, 0)

    fun setValue(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String) : String? {
        return sharedPreferences.getString(key, "")
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}