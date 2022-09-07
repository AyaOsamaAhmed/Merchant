package com.rbt.merchant.domain.use_case.ui_models.order_details


data class OrderDetailsStatusModel (
        val id : Int ,
        var status : Int? = null ,
        var time : String
)