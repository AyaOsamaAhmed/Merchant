package com.rbt.merchant.domain.data.remote.model.response.auth_feature.resend_otp

import kotlinx.serialization.Serializable

@Serializable
data class ResendOtpResponseModel(
    val `data`: Data?=null,
    val message: String?=null,
    val `set`: Int?=null
)