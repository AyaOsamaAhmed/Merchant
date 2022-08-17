package com.rbt.merchant.domain.data.remote.service

import com.google.gson.Gson
import com.rbt.merchant.domain.data.remote.http_connection.client.KtorClient
import com.rbt.merchant.domain.data.remote.http_connection.apis.AuthApi
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.confirm_otp.ConfirmOtpResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.logout.LogoutResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth.PhoneAuthResponseModel
import com.rbt.merchant.domain.data.remote.model.response.auth_feature.resend_otp.ResendOtpResponseModel
import com.rbt.merchant.utils.constants.Constant
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.nio.charset.Charset

/**
 * AuthServicesImpl is the implementation of the all apis related to auth services
 * it use instance from KtorClient api make a https connection
 * override to the suspend function from AuthApi
 */

private const val TAG = "AuthServicesImpl"
object AuthServicesImpl : AuthApi {

    private val httpClient by lazy {
        KtorClient.getInstance
    }

    override suspend fun sendOtpToPhoneNumber(phoneNumber: String): PhoneAuthResponseModel? {
        return try {
            // hit base url with the endpoint for sign up process
            // with phoneNumber as parameter for api
            // and check if the api throw any exception
            httpClient.post(path = Constant.SIGNUP_API) {
                parameter("phoneNumber", phoneNumber)
            }
        } catch (cre: ClientRequestException) {
            // convert the string value throws with exception to its response data model using Gson lib
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, PhoneAuthResponseModel::class.java)
        } catch (cre: RedirectResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, PhoneAuthResponseModel::class.java)
        } catch (cre: ServerResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, PhoneAuthResponseModel::class.java)
        }
    }

    override suspend fun confirmOTP(otp: String,phoneNumber:String): ConfirmOtpResponseModel? {
        return try {
            // hit base url with the endpoint for confirm otp process
            // with otp and phoneNumber as parameters for api
            // and check if the api throw any exception
            httpClient.post(path = Constant.CONFIRM_OTP_API) {
                parameter("otp", otp)
                parameter("phoneNumber", phoneNumber)
            }
        } catch (cre: ClientRequestException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, ConfirmOtpResponseModel::class.java)
        } catch (cre: RedirectResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, ConfirmOtpResponseModel::class.java)
        } catch (cre: ServerResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, ConfirmOtpResponseModel::class.java)
        }
    }
    override suspend fun resendOtpToPhoneNumber(phoneNumber: String): ResendOtpResponseModel? {
        return try {
            // hit base url with the endpoint for resend otp process
            // with phoneNumber as parameters for api
            // and check if the api throw any exception
            httpClient.post(path = Constant.RESEND_OTP) {
                parameter("phoneNumber", phoneNumber)
            }
        } catch (cre: ClientRequestException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, ResendOtpResponseModel::class.java)
        } catch (cre: RedirectResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, ResendOtpResponseModel::class.java)
        } catch (cre: ServerResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, ResendOtpResponseModel::class.java)
        }
    }

    override suspend fun logoutUser(): LogoutResponseModel? {
        return try {
            // hit base url with the endpoint for logout process
            // without any parameters for api
            // and check if the api throw any exception
            httpClient.post(path = Constant.LOGOUT_API)
        } catch (cre: ClientRequestException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, LogoutResponseModel::class.java)
        } catch (cre: RedirectResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, LogoutResponseModel::class.java)
        } catch (cre: ServerResponseException) {
            val content = cre.response.readText(Charset.defaultCharset())
            Gson().fromJson(content, LogoutResponseModel::class.java)
        }
    }
}