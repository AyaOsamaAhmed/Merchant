package com.rbt.merchant.domain.data.remote.model.response.auth_feature.confirm_otp

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val session: String?=null
)