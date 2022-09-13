package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat

private const val TAG = "ChatSettingViewModel"
class ChatSettingViewModel: ViewModel() {
    var requestRelatedOrdersLiveData = MutableLiveData<ArrayList<String>>()
    private var relatedOrdersData: ArrayList<String> = arrayListOf()
    var requestRelatedComplaintsLiveData = MutableLiveData<ArrayList<String>>()
    private var relatedComplaintsData: ArrayList<String> = arrayListOf()
    var requestRelatedInquiriesLiveData = MutableLiveData<ArrayList<String>>()
    private var relatedInquiriesData: ArrayList<String> = arrayListOf()

    private val image2 =
        "https://rbt-merchant-assets.s3.eu-central-1.amazonaws.com/images/product-img.png"


    init {

        getLocalDataChat()
    }


    private fun getLocalDataChat() {
        repeat(10) { i ->
            relatedOrdersData.add("طلب رقم1225871$i ")
        }
        requestRelatedOrdersLiveData.value = relatedOrdersData
        repeat(10) { i ->
            relatedComplaintsData.add("طلب رقم1225871$i ")
        }
        requestRelatedComplaintsLiveData.value = relatedComplaintsData
        repeat(10) { i ->
            relatedInquiriesData.add("طلب رقم1225871$i ")
        }
        requestRelatedInquiriesLiveData.value = relatedInquiriesData
    }
    fun onClickTransferToOrder(view: View){
        val context = view.context
        Log.d(TAG, "onClickTransferToOrder: تحويل لطلب")
        Toast.makeText(context,"تحويل لطلب",Toast.LENGTH_SHORT).show()
    }
    fun onClickTransferToComplaint(view: View){
        val context = view.context
        Log.d(TAG, "onClickTransferToComplaint: تحويل لشكوى")
        Toast.makeText(context,"تحويل لشكوى",Toast.LENGTH_SHORT).show()
    }
    fun onClickTransferToInquiry(view: View){
        val context = view.context
        Log.d(TAG, "onClickTransferToInquiry: تحويل لاستفسارات")
        Toast.makeText(context,"تحويل لاستفسارات",Toast.LENGTH_SHORT).show()
    }
}