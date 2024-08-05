package com.angellira.paris.preferences

import android.content.Context
import java.util.UUID

const val USER_PREFERENCES = "USER_PREFERENCES"

class PreferencesManager (context: Context){

    var userId: String?
        get() = sharedPreferences.getString(ID_USUARIO, null)
        set(value) = sharedPreferences.edit().putString(ID_USUARIO, value).apply()

    private val sharedPreferences =
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)

    var isLogged: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED, false)
        set(value) = sharedPreferences.edit().putBoolean(IS_LOGGED, value).apply()

    companion object {
        private const val IS_LOGGED = "logou"
        private const val ID_USUARIO = "Id"
    }

    fun logout() {
        sharedPreferences.edit().putBoolean(USER_PREFERENCES, false).clear().apply()
    }

}