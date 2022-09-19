package com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat

class OrdersViewModel: ViewModel() {
    var requestBranchesListLiveData = MutableLiveData<ArrayList<String>>()
    private var branchesData: ArrayList<String> = arrayListOf()

    init {

        getLocalDataChat()
    }
    private fun getLocalDataChat() {
        repeat(10) { i ->
            branchesData.add("اسم الفرع $i")
        }
        requestBranchesListLiveData.value = branchesData
    }
}