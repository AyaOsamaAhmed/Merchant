package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

class BranchManagingViewModel : ViewModel() {
    var requestProductsListLiveData = MutableLiveData<ArrayList<ProductOrderDetailsModel>>()
    private var productsListData: ArrayList<ProductOrderDetailsModel> = arrayListOf()

    private val image2 =
        "https://rbt-merchant-assets.s3.eu-central-1.amazonaws.com/images/product-img.png"


    init {

        getLocalDataChat()
    }


    private fun getLocalDataChat() {
        repeat(10) { i ->
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
        requestProductsListLiveData.value = productsListData
    }
}