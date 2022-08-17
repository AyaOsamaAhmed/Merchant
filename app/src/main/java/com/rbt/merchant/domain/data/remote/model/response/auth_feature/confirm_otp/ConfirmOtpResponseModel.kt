package com.rbt.merchant.domain.data.remote.model.response.auth_feature.confirm_otp

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmOtpResponseModel(
    val `data`: Data?=null,
    val message: String?=null,
    val `set`: Int?=null
)