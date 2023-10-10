package com.example.monogram

import android.content.Context
import android.content.SharedPreferences

enum class SharedPreferencesScopes {
    GENERAL, LAST_SCOPE
}

enum class SharedPreferencesValues {
    SETTINGS_SCOPE
}

class SharedPreferences private constructor(context: Context) {
    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
            SharedPreferencesScopes.GENERAL.toString(), Context.MODE_PRIVATE
        )
    }

    // region strings
    fun writeString(scope: SharedPreferencesScopes, value: SharedPreferencesValues) {
        sharedPreferences.edit().putString(scope.toString(), value.toString()).apply()
    }

    fun getString(scope: SharedPreferencesScopes): String? {
        return sharedPreferences.getString(scope.toString(), null)
    }
    // endregion

    fun remove(scope: SharedPreferencesScopes) {
        sharedPreferences.edit().remove(scope.toString()).apply()
    }

    companion object {
        @Volatile
        private var instance: com.example.monogram.SharedPreferences? = null

        fun getInstance(context: Context): com.example.monogram.SharedPreferences? {
            return if (instance == null) SharedPreferences(context)
            else instance
        }
    }
}