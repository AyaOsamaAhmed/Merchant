package com.rbt.merchant.repository

import com.rbt.merchant.domain.data.remote.model.response.auth_feature.confirm_otp.ConfirmOtpResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.logout.LogoutResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth.PhoneAuthResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.resend_otp.ResendOtpResponseModel
import com.rbt.merchant.domain.data.remote.service.AuthServicesImpl

/**
 * PhoneAuthRepoIml is the implementation of the all apis related to auth services
 * this is the class where the viewModel deal with it to use the services
 * All functions in this class return the response for the equivalent function at AuthServicesImpl
 * All functions is a suspend function
 * because it call a suspend functions and invokes from coroutine scope with IO Dispatcher
 *
 */
object PhoneAuthRepoIml {
    suspend fun sendOTP(phoneNumber:String): PhoneAuthResponseModel? {
        return AuthServicesImpl.sendOtpToPhoneNumber(phoneNumber)
    }
    suspend fun confirmOTP(otp:String,phoneNumber:String): ConfirmOtpResponseModel? {
        return AuthServicesImpl.confirmOTP(otp,phoneNumber)
    }
    suspend fun resendOTP(phoneNumber:String): ResendOtpResponseModel? {
        return AuthServicesImpl.resendOtpToPhoneNumber(phoneNumber)
    }
    suspend fun logoutUser(): LogoutResponseModel? {
        return AuthServicesImpl.logoutUser()
    }
}