package com.rbt.merchant.presentation.fragment.home.main.chat.all_chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat

class ChatViewModel : ViewModel() {

    var requestChatListLiveData = MutableLiveData<ArrayList<Chat>>()
    private var chatData: ArrayList<Chat> = arrayListOf()
    var requestBranchesListLiveData = MutableLiveData<ArrayList<String>>()
    private var branchesData: ArrayList<String> = arrayListOf()

    private val image2 =
        "https://rbt-merchant-assets.s3.eu-central-1.amazonaws.com/images/product-img.png"


    init {

        getLocalDataChat()
    }


    private fun getLocalDataChat() {
        repeat(10) { i ->
            chatData.add(Chat(i, image2, "userName $i", "last Message $i", "2:0$i", i + 10))
        }
        requestChatListLiveData.value = chatData
        repeat(10) { i ->
            branchesData.add("اسم الفرع $i")
        }
        requestBranchesListLiveData.value = branchesData
    }


}