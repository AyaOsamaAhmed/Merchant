package com.rbt.merchant.utils.common

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rbt.merchant.utils.constants.LanguageConstant
import com.rbt.merchant.utils.sharedPref
import java.util.*


private const val TAG = "LocaleHelper"

object LocaleHelper {

    fun applyLanguageContext(
         mContext: Context,
        local: Locale?,
        activity: AppCompatActivity? = null
    ): Context {
        if (local == null) return mContext
        if (local == getLocale(mContext.resources.configuration)) return mContext

        return try {
            setupLocale(local)
            val resources = mContext.resources
            val configuration = getOverridingConfig(local, resources)
            Log.d(TAG, "applyLanguageContext: ${local.language}")
            if (activity != null) {
                setLayoutDirection(activity)
            }
            updateResources(mContext, resources, configuration)
            mContext.createConfigurationContext(configuration)
        } catch (exception: Exception) {
            mContext
        }
    }

    private fun updateResources(context: Context, resources: Resources, config: Configuration) {
        resources.updateConfiguration(config, resources.displayMetrics)
        if (context.applicationContext !== context) {
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    private fun setupLocale(locale: Locale) {
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.setDefault(LocaleList(locale))
        }
    }

    private fun getOverridingConfig(locale: Locale, resources: Resources): Configuration {
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(LocaleList(locale))
        } else {
            configuration.locale = locale
        }
        return configuration
    }

    private fun getLocale(configuration: Configuration): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.get(0)
        } else {
            configuration.locale
        }
    }

    private fun setLayoutDirection(activity: AppCompatActivity) {
        if (sharedPref.appLanguagePref == LanguageConstant.JORDAN_LANG) {
            activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
    }

    fun saveLanguageToSharedPref(language: String) {
        sharedPref.appLanguagePref = language
    }

    fun reCreateActivity(activity: Activity) {
        activity.finish()
        activity.startActivity(activity.intent)
    }
}