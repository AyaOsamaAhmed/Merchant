package com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val emailAddress: String?=null,
    val fullNameAr: String?=null,
    val fullNameEn: String?=null,
    val id: Int?=null,
    val phoneNumber: String?=null
)