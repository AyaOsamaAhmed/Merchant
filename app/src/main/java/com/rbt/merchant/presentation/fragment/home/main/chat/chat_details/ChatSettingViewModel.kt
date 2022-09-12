package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel

private const val TAG = "ChatSettingViewModel"
class ChatSettingViewModel: ViewModel() {

    fun onClickTransferToOrder(view: View){
        val context = view.context
        Log.d(TAG, "onClickTransferToOrder: تحويل لطلب")
        Toast.makeText(context,"تحويل لطلب",Toast.LENGTH_LONG).show()
    }
    fun onClickTransferToComplaint(view: View){
        val context = view.context
        Log.d(TAG, "onClickTransferToComplaint: تحويل لشكوى")
        Toast.makeText(context,"تحويل لشكوى",Toast.LENGTH_LONG).show()
    }
    fun onClickTransferToInquiry(view: View){
        val context = view.context
        Log.d(TAG, "onClickTransferToInquiry: تحويل لاستفسارات")
        Toast.makeText(context,"تحويل لاستفسارات",Toast.LENGTH_LONG).show()
    }
    fun onClickRelatedOrder(view: View){
        val context = view.context
        Log.d(TAG, "onClickRelatedOrder: الطلبات المرتبطة")
        Toast.makeText(context,"الطلبات المرتبطة",Toast.LENGTH_LONG).show()
    }
    fun onClickRelatedComplaint(view: View){
        val context = view.context
        Log.d(TAG, "onClickRelatedComplaint:  الشكاوي المرتبطة")
        Toast.makeText(context,"الشكاوي المرتبطة",Toast.LENGTH_LONG).show()
    }
    fun onClickRelatedInquiry(view: View){
        val context = view.context
        Log.d(TAG, "onClickRelatedInquiry: الاستفسارات المرتبطة")
        Toast.makeText(context,"الاستفسارات المرتبطة",Toast.LENGTH_LONG).show()
    }
}