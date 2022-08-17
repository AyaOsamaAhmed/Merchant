package com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val user: User? = null,
    val otp: String? = null
)