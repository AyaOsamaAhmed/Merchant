package com.rbt.merchant.domain.use_case.ui_models.order_details

data class ProductOrderDetailsModel(
    val id : Int ,
    val product_name:String,
    val product_number:String,
    val product_price:String,
    val product_image:String,
    val colorList:List<String>
)