package com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.new_chivalry_rbt.NewChivalryRbtModel
import com.rbt.merchant.domain.use_case.ui_models.new_orders.NewOrdersModel

class MainOrderViewModel: ViewModel() {
    var requestOrdersListLiveData = MutableLiveData<ArrayList<NewOrdersModel>>()
    private var newOrdersData: ArrayList<NewOrdersModel> = arrayListOf()
    private val image2 =
        "https://rbt-merchant-assets.s3.eu-central-1.amazonaws.com/images/product-img.png"

    init {

        getLocalDataChat()
    }

    private fun getLocalDataChat() {
        repeat(10) { i ->
            newOrdersData.add(NewOrdersModel(i, "12358958$i","client $i", "10/07/2022"))
        }
        requestOrdersListLiveData.value = newOrdersData
    }
}