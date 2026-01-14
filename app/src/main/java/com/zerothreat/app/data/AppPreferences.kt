// File: app/src/main/java/com/zerothreat/app/data/AppPreferences.kt
package com.zerothreat.app.data

import android.content.Context
import android.content. SharedPreferences

class AppPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("zerothreat_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FIRST_LAUNCH = "is_first_launch"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_PERMISSIONS_GRANTED = "permissions_granted"
        private const val KEY_MODE_SELECTED = "mode_selected"
        private const val KEY_SELECTED_MODE = "selected_mode"
    }

    var isFirstLaunch: Boolean
        get() = prefs.getBoolean(KEY_FIRST_LAUNCH, true)
        set(value) = prefs.edit().putBoolean(KEY_FIRST_LAUNCH, value).apply()

    var onboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) = prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, value).apply()

    var permissionsGranted: Boolean
        get() = prefs.getBoolean(KEY_PERMISSIONS_GRANTED, false)
        set(value) = prefs.edit().putBoolean(KEY_PERMISSIONS_GRANTED, value).apply()

    var modeSelected: Boolean
        get() = prefs.getBoolean(KEY_MODE_SELECTED, false)
        set(value) = prefs.edit().putBoolean(KEY_MODE_SELECTED, value).apply()

    var selectedMode: String
        get() = prefs.getString(KEY_SELECTED_MODE, "MANUAL") ?: "MANUAL"
        set(value) = prefs.edit().putString(KEY_SELECTED_MODE, value).apply()

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}