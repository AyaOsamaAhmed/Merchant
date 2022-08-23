package com.rbt.merchant.domain.use_case.ui_models.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val id: Int,
    val image: String,
    val title: String? = null,
    val last_message: String? = null,
    val time: String? = null,
    val unRead: Int? = null
): Parcelable