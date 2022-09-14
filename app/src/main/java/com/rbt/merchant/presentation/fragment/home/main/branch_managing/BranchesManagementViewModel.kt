package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BranchesManagementViewModel : ViewModel() {
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