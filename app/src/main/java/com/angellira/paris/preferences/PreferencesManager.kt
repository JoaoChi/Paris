package com.angellira.paris.preferences

import android.content.Context
import java.util.UUID

const val ID_USER = "id"
const val USER_PREFERENCES = "USER_PREFERENCES"

class PreferencesManager (context: Context){

    private val sharedPreferences =
        context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)

    var isLogged: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED, false)
        set(value) = sharedPreferences.edit().putBoolean(IS_LOGGED, value).apply()

    companion object {
        private const val IS_LOGGED = "logou"
    }

    fun getUserID():String{
        var userId = sharedPreferences.getString(ID_USER, null)
        if (userId == null) {
            userId = generateNewUserId()
            saveUserId(userId)
        }
        return userId
    }

    private fun generateNewUserId(): String {
        return UUID.randomUUID().toString()
    }

    private fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(ID_USER, userId).apply()
    }

    fun logout() {
        sharedPreferences.edit().putBoolean(USER_PREFERENCES, false).clear().apply()
    }

}