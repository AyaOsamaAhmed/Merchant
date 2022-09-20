package com.rbt.merchant.presentation.fragment.home.main.chat.price_quote

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel
import okhttp3.internal.notify

private const val TAG = "PriceQuoteViewModel"
class PriceQuoteViewModel : ViewModel() {
    var requestProductsListLiveData = MutableLiveData<ArrayList<ProductOrderDetailsModel>>()
    private var productsListData: ArrayList<ProductOrderDetailsModel> = arrayListOf()
    var requestAddProductsListLiveData = MutableLiveData<ArrayList<ProductOrderDetailsModel>>()
    private var addProductsListData: ArrayList<ProductOrderDetailsModel> = arrayListOf()

    private val image2 =
        "https://rbt-merchant-assets.s3.eu-central-1.amazonaws.com/images/product-img.png"


    init {
        getLocalDataChat()
    }


    private fun getLocalDataChat() {
        repeat(2) { i ->
            if(i%2==0){
                productsListData.add(
                    ProductOrderDetailsModel(
                        id = i,
                        product_name = "العطر الازرق",
                        product_image = image2,
                        product_number = "5",
                        product_price = 200,
                        product_old_price = 500,
                        product_availability = true,
                        colorList = listOf("ابيض", "ابيض", "ابيض")
                    )
                )
            }else {
                productsListData.add(
                    ProductOrderDetailsModel(
                        id = i,
                        product_name = "العطر الازرق",
                        product_image = image2,
                        product_number = "5",
                        product_price = 200,
                        product_availability = true,
                        colorList = listOf("ابيض", "ابيض", "ابيض")
                    )
                )
            }
        }
        repeat(4) { i ->
            if(i%2==0){
                addProductsListData.add(
                    ProductOrderDetailsModel(
                        id = i,
                        product_name = "العطر الازرق",
                        product_image = image2,
                        product_number = "5",
                        product_price = 200,
                        product_old_price = 500,
                        product_availability = true,
                        colorList = listOf("ابيض", "ابيض", "ابيض")
                    )
                )
            }else {
                addProductsListData.add(
                    ProductOrderDetailsModel(
                        id = i,
                        product_name = "العطر الازرق",
                        product_image = image2,
                        product_number = "5",
                        product_price = 200,
                        product_availability = true,
                        colorList = listOf("ابيض", "ابيض", "ابيض")
                    )
                )
            }
        }
        requestProductsListLiveData.value = productsListData
        requestAddProductsListLiveData.value = addProductsListData
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addProduct(product:ProductOrderDetailsModel){
        productsListData.add(
            ProductOrderDetailsModel(
                id = product.id,
                product_name = product.product_name,
                product_image = product.product_image,
                product_number = product.product_number,
                product_price = product.product_price,
                product_old_price = product.product_old_price,
                product_availability = product.product_availability,
                colorList = product.colorList
            )
        )
        requestProductsListLiveData.postValue(productsListData)
    }
}