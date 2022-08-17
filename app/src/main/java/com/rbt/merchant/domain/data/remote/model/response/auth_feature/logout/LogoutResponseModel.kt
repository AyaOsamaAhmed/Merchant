package com.rbt.merchant.domain.data.remote.model.response.auth_feature.logout

import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponseModel(
    //val `data`: Any?=null,
    val message: String?=null,
    val `set`: Int?=null
)