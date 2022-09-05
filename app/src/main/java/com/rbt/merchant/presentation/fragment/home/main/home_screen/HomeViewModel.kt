package com.rbt.merchant.presentation.fragment.home.main.home_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.new_chivalry_rbt.NewChivalryRbtModel
import com.rbt.merchant.domain.use_case.ui_models.new_orders.NewOrdersModel

class HomeViewModel :ViewModel() {
    var requestChatListLiveData = MutableLiveData<ArrayList<Chat>>()
    var requestOrdersListLiveData = MutableLiveData<ArrayList<NewOrdersModel>>()
    var requestChivalryRbtListLiveData = MutableLiveData<ArrayList<NewChivalryRbtModel>>()
    private var chatData: ArrayList<Chat> = arrayListOf()
    private var newOrdersData: ArrayList<NewOrdersModel> = arrayListOf()
    private var newChivalryData: ArrayList<NewChivalryRbtModel> = arrayListOf()

    private val image2 =
        "https://rbt-merchant-assets.s3.eu-central-1.amazonaws.com/images/product-img.png"


    init {

        getLocalDataChat()
    }


    private fun getLocalDataChat() {
        repeat(10) { i ->
            chatData.add(Chat(i, image2, "userName $i", "last Message $i", "2:0$i", i + 10))
        }
        repeat(10) { i ->
            newOrdersData.add(NewOrdersModel(i, "12358958$i","client $i", "10/07/2022"))
        }
        repeat(10) { i ->
            newChivalryData.add(NewChivalryRbtModel(i, "12358958$i","client $i", "10/07/2022"))
        }
        requestChatListLiveData.value = chatData
        requestOrdersListLiveData.value = newOrdersData
        requestChivalryRbtListLiveData.value = newChivalryData
    }
}