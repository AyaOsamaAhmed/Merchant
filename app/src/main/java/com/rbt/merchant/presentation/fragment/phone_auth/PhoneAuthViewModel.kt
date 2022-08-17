package com.rbt.merchant.presentation.fragment.phone_auth

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth.PhoneAuthResponseModel
import com.rbt.merchant.repository.PhoneAuthRepoIml
import com.rbt.merchant.utils.common.CommonFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "PhoneAuthViewModel"
class PhoneAuthViewModel : ViewModel() {
    // variable observe phone number entered by user
    var phone = MutableLiveData<String>()
    // variable observe response from sign up api
    var authResponse = MutableLiveData<PhoneAuthResponseModel>()

    //impl onClick on Start Journey button from xml data binding
    // 1- check phone number validation is null or Saudi mobile number
    // 2- send otp code to valid phone number
    fun onClickSendOtpBtn(view: View){
        if (checkPhoneNumberValidation(phone.value)) {
            Log.d(TAG, "onViewCreated: phoneNumber: ${phone.value}")
            sendOTP(phone.value!!)
        }else{
            CommonFunction.showToast(view.context,"This number not a Saudi phone number")
        }
    }
    private fun checkPhoneNumberValidation(phoneNumber: String?): Boolean {
        // saudi phone number pattern
        val validNumbers = listOf('3', '4', '5', '5', '9')
        if (phoneNumber == null)
            return false
        else if (phoneNumber[0] == '0' && phoneNumber.length == 10) {
            return validNumbers.contains(phoneNumber[1])
        }
        return false
    }
    // hit api from phone auth repository implementation
    private fun sendOTP(phoneNumber: String) = viewModelScope.launch( Dispatchers.IO) {
        // post response value to authResponse variable when return from api
        authResponse.postValue(PhoneAuthRepoIml.sendOTP(phoneNumber))
    }
}