package com.rbt.merchant.domain.data.remote.model.response.auth_feature.resend_otp

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val otp: String?=null
)