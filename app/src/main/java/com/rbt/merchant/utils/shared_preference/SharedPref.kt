package com.rbt.merchant.utils.shared_preference

import android.content.Context
import android.content.SharedPreferences
import com.rbt.merchant.utils.constants.SharedPrefConstant

/**
 * A SharedPreferences object points to a file containing key-value pairs and provides simple methods to read and write them.
 * https://developer.android.com/reference/android/content/SharedPreferences
 */
class SharedPref(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(SharedPrefConstant.APP_PREF_1,Context.MODE_PRIVATE)

    var userTokenPref: String?
        get() = preferences.getString(SharedPrefConstant.APP_PREF_USER_TOKEN, "")
        set(value) = preferences.edit().putString(SharedPrefConstant.APP_PREF_USER_TOKEN, value).apply()
    var userActiveStatePref: Boolean
        get() = preferences.getBoolean(SharedPrefConstant.APP_PREF_USER_ACTIVE_STATE, false)
        set(value) = preferences.edit().putBoolean(SharedPrefConstant.APP_PREF_USER_ACTIVE_STATE, value).apply()
    var appLanguagePref: String?
        get() = preferences.getString(SharedPrefConstant.APP_PREF_SELECTED_LANGUAGE, "")
        set(value) = preferences.edit().putString(SharedPrefConstant.APP_PREF_SELECTED_LANGUAGE, value).apply()

    fun clear() {
        preferences.edit().clear().apply()
    }
    fun removeKey(key: String) {
        preferences.edit().remove(key).apply()
    }
}