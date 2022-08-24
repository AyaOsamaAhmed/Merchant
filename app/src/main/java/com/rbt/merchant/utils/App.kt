package com.rbt.merchant.utils

import android.app.Application
import androidx.activity.result.ActivityResultLauncher
import com.rbt.merchant.utils.shared_preference.SharedPref

/**
 * App contain the shared objects to access it from all the app
*/

val sharedPref: SharedPref by lazy {
    App.prefs!!
}
val permissionRequestList: MutableList<String>  by lazy {
    App.permissionRequest
}
class App : Application() {
    companion object {
        var prefs: SharedPref? = null
        lateinit var instance: App
        lateinit var permissionRequest:MutableList<String>
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = SharedPref(applicationContext)
         permissionRequest = ArrayList()
    }
}