package com.angellira.paris.preferences

import android.content.Context

const val USER_PREFERENCES = "USER_PREFERENCES"

class PreferencesManager (context: Context){

//    lateinit var userId: String

    private val sharedPreferences =
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)

    var isLogged: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED, false)
        set(value) = sharedPreferences.edit().putBoolean(IS_LOGGED, value).apply()

    companion object {
        private const val IS_LOGGED = "logou"
    }

    fun logout() {
        sharedPreferences.edit().putBoolean(USER_PREFERENCES, false).clear().apply()
    }

}