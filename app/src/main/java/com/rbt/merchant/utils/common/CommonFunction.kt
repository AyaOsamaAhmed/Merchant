package com.rbt.merchant.utils.common

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.rbt.merchant.utils.constants.LanguageConstant

/**
 * the shared and common function
 */
object CommonFunction{
    fun openCustomFragment(activity:FragmentActivity,containerId:Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .disallowAddToBackStack()
            .commit()
    }
    fun showToast(context: Context,message:String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    fun onClickJordanLang(activity: Activity){
        LocaleHelper.saveLanguageToSharedPref(LanguageConstant.JORDAN_LANG)
        LocaleHelper.reCreateActivity(activity)
    }
    fun onClickIndiaLang(activity: Activity){
        LocaleHelper.saveLanguageToSharedPref(LanguageConstant.INDIA_LANG)
        LocaleHelper.reCreateActivity(activity)
    }

    fun onClickEnglishLang(activity: Activity) {
        LocaleHelper.saveLanguageToSharedPref(LanguageConstant.ENGLISH_LANG)
        LocaleHelper.reCreateActivity(activity)
    }
    fun convertToArabic(value: String): String {
        return value
            .replace("1".toRegex(), "١").replace("2".toRegex(), "٢")
            .replace("3".toRegex(), "٣").replace("4".toRegex(), "٤")
            .replace("5".toRegex(), "٥").replace("6".toRegex(), "٦")
            .replace("7".toRegex(), "٧").replace("8".toRegex(), "٨")
            .replace("9".toRegex(), "٩").replace("0".toRegex(), "٠")
    }
}
