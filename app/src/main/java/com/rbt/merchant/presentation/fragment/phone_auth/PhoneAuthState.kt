package com.rbt.merchant.presentation.fragment.phone_auth

import com.rbt.merchant.domain.data.remote.model.response.auth_feature.phone_auth.PhoneAuthResponseModel

data class PhoneAuthState(
    var isLoading: Boolean? = false,
    var phoneAuthResponseModel: PhoneAuthResponseModel? = null,
    var error: String? = "Something went wrong"
)