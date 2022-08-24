package com.rbt.merchant.utils.common

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.rbt.merchant.utils.constants.LanguageConstant
import java.text.SimpleDateFormat
import java.util.*

/**
 * the shared and common function
 */
private const val SECOND_MILLIS: Int = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
object CommonFunction{
    fun openCustomFragment(activity:FragmentActivity,containerId:Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            //.disallowAddToBackStack()
            .addToBackStack(null)
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
    fun getCurrentTime(): String {
        val simpleDate = SimpleDateFormat("hh:mm a")
        return simpleDate.format(Date())
    }
    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            diff.div(MINUTE_MILLIS) .toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            diff.div(HOUR_MILLIS).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
            diff.div(DAY_MILLIS).toString() + " days ago"
        }
    }
}
