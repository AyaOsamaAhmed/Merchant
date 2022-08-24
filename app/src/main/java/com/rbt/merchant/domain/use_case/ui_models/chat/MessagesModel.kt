package com.rbt.merchant.domain.use_case.ui_models.chat

data class MessagesModel (
    val id: Int?=null,
    var viewType:Int?=null,
    /*val sender: String?=null,
    val receiver: String?=null,*/
    val imageURL: String?=null,
    val messageTime: String?=null,
    val message: String?=null,
    val messageType: String?=null,
    val timeStamp: String?=null
)