package com.rbt.merchant.presentation.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.rbt.merchant.R
import com.rbt.merchant.utils.common.LocaleHelper
import com.rbt.merchant.utils.constants.LanguageConstant
import com.rbt.merchant.utils.sharedPref
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!sharedPref.appLanguagePref.isNullOrEmpty()) {
            LocaleHelper.applyLanguageContext(
                mContext = this,
                Locale(sharedPref.appLanguagePref!!),
                this
            )
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        Log.d(TAG, "attachBaseContext: in")
        Log.d(TAG, "attachBaseContext: sharedPref.appLanguagePref ${sharedPref.appLanguagePref}")
        if(newBase == null){
            Log.d(TAG, "attachBaseContext: new base context is null ")
        }else {
            if (sharedPref.appLanguagePref.equals(LanguageConstant.JORDAN_LANG)) {
                super.attachBaseContext(
                    LocaleHelper.applyLanguageContext(
                        newBase,
                        Locale(LanguageConstant.JORDAN_LANG),
                        this
                    )
                )
            }else if (sharedPref.appLanguagePref.equals(LanguageConstant.INDIA_LANG)){
                super.attachBaseContext(
                    LocaleHelper.applyLanguageContext(
                        newBase,
                        Locale(LanguageConstant.INDIA_LANG),
                        this
                    )
                )
            }else if (sharedPref.appLanguagePref.equals(LanguageConstant.ENGLISH_LANG)){
                super.attachBaseContext(
                    LocaleHelper.applyLanguageContext(
                        newBase,
                        Locale(LanguageConstant.ENGLISH_LANG),
                        this
                    )
                )
            }else{
                Log.d(TAG, "attachBaseContext: shared pref lang is null ")
                super.attachBaseContext(
                    newBase
                )
            }
        }

    }

    override fun recreate() {
        super.recreate()
     //   findViewById<ProgressBar>(R.id.progress_circular).visibility = View.VISIBLE
    }

}