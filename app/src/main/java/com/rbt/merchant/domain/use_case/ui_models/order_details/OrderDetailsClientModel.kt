package com.rbt.merchant.domain.use_case.ui_models.order_details

data class OrderDetailsClientModel (
    val client_name: String,
    val client_imgURL:String,
    val client_phoneNumber:String,
    val client_whatsupURL:String,
    val client_chatID:String
)