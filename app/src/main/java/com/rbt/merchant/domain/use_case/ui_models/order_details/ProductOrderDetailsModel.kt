package com.rbt.merchant.domain.use_case.ui_models.order_details

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductOrderDetailsModel(
    val id : Int,
    val product_name:String,
    val product_number:String,
    val product_price:Int,
    val product_old_price:Int?=null,
    val product_image:String,
    val product_availability:Boolean?=false,
    val colorList:List<String>
): Parcelable
