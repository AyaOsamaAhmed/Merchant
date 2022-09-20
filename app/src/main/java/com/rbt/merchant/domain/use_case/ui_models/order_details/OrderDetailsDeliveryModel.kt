package com.rbt.merchant.domain.use_case.ui_models.order_details

data class OrderDetailsDeliveryModel(
    val delivery_name:String,
    val delivery_imgURL:String,
    val delivery_phoneNumber:String,
    val delivery_whatsupURL:String,
    val delivery_chatID:String,
)
