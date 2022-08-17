package com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth

import kotlinx.serialization.Serializable

@Serializable
data class PhoneAuthResponseModel(
    val `data`: Data?=null,
    val message: String?=null,
    val `set`: Int?=null
)