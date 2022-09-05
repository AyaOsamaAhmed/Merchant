package com.rbt.merchant.domain.use_case.ui_models.new_orders

data class NewOrdersModel(
    val id:Int,
    val order_Id:String,
    val client_name:String,
    val order_date:String
)
