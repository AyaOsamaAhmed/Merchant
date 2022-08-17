package com.rbt.merchant.utils

import android.app.Application
import com.rbt.merchant.utils.shared_preference.SharedPref

/**
 * App contain the shared objects to access it from all the app
*/

val sharedPref: SharedPref by lazy {
    App.prefs!!
}
class App : Application() {
    companion object {
        var prefs: SharedPref? = null
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = SharedPref(applicationContext)
    }
}