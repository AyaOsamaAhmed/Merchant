package com.rbt.merchant.presentation.fragment.confirm_otp

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.confirm_otp.ConfirmOtpResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.resend_otp.ResendOtpResponseModel
import com.rbt.merchant.repository.PhoneAuthRepoIml
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ConfirmOtpViewModel"
class ConfirmOtpViewModel : ViewModel() {
    // variable observe otp entered by user
    var otp = MutableLiveData<String>()
    // variable observe phone number received from args
    var phoneNumber = MutableLiveData<String>()
    // variable observe response from confirm otp api
    var confirmOtpResponse = MutableLiveData<ConfirmOtpResponseModel>()
    // variable observe response from resend otp api
    var resendOtpResponse = MutableLiveData<ResendOtpResponseModel>()

    //impl onClick on verify code button from xml data binding
    // 1- check otp validation is empty or not
    fun onClickVerifyBtn(view:View){
        var context = view.context
        if (checkOtpValidation(otp.value!!)) {
            Log.d(TAG, "onViewCreated: otp: $otp")
            confirmOTP(otp.value!!,phoneNumber.value!!)
        }
    }
    //impl onClick on Resent Code button from xml data binding
    // 1- check phone number validation is empty or not
    // 2- send otp code to valid phone number
    fun onClickResendBtn(view:View){
        var context = view.context
        if (checkReceivedPhoneNumber(phoneNumber.value!!)) {
            Log.d(TAG, "onViewCreated: phoneNumber: $otp")
            resendOtp(phoneNumber.value!!)
        }
    }
    private fun checkOtpValidation(otp: String): Boolean {
        if (otp.isEmpty())
            return false
        return true
    }

    private fun checkReceivedPhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isEmpty())
            return false
        return true
    }
    // hit api from phone auth repository implementation
    private fun confirmOTP(otp: String,phoneNumber:String) = viewModelScope.launch( Dispatchers.IO) {
        // post response value to confirmOtpResponse variable when return from api
        confirmOtpResponse.postValue(PhoneAuthRepoIml.confirmOTP(otp,phoneNumber))
    }
    // hit api from phone auth repository implementation
    private fun resendOtp(phoneNumber: String) = viewModelScope.launch( Dispatchers.IO) {
        // post response value to resendOtpResponse variable when return from api
        resendOtpResponse.postValue(PhoneAuthRepoIml.resendOTP(phoneNumber))
    }

}