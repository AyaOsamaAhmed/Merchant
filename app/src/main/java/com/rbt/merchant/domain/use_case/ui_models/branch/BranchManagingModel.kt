package com.rbt.merchant.domain.use_case.ui_models.branch

import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

data class BranchManagingModel(
    val id:Int,
    val branch_Id:String,
    val branch_name:String,
    val branch_location:String,
    val branch_products:List<ProductOrderDetailsModel>
)
