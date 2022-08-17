package com.rbt.merchant.domain.data.remote.http_connection.apis

import com.rbt.merchant.domain.data.remote.model.response.auth_feature.confirm_otp.ConfirmOtpResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.logout.LogoutResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth.PhoneAuthResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.resend_otp.ResendOtpResponseModel

/**
 * AuthApi is the main interface to prepare the functions that represent the Restful apis
 * for authentication service with its response model or its parameters
 */
interface AuthApi {

    suspend fun sendOtpToPhoneNumber(
        phoneNumber: String
    ): PhoneAuthResponseModel?

    suspend fun confirmOTP(
        otp: String,
        phoneNumber: String
    ): ConfirmOtpResponseModel?

    suspend fun resendOtpToPhoneNumber(
        phoneNumber: String
    ): ResendOtpResponseModel?

    suspend fun logoutUser(): LogoutResponseModel?
}