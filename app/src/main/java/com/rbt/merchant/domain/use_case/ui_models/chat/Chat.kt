package com.rbt.merchant.domain.use_case.ui_models.chat

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: Int,
    val image: String,
    val title: String? = null,
    val last_message: String? = null,
    val time: String? = null,
    val unRead: Int? = null
)