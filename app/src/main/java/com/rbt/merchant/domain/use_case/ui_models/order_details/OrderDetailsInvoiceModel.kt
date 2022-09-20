package com.rbt.merchant.domain.use_case.ui_models.order_details

data class OrderDetailsInvoiceModel(
    val order_purchases: String,
    val order_tax: String,
    val order_delivery: String
)
