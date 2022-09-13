package com.rbt.merchant.presentation.fragment.home.main.chat.all_chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentChatsListBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.presentation.ui.MainActivity


class ChatsListFragment(private var position:Int) : Fragment() {
    private lateinit var binding:FragmentChatsListBinding
    private lateinit var viewModel : ChatViewModel
    private lateinit var adapter: ChatAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsListBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        adapter = ChatAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testTxt.text = position.toString()
        viewModel.requestChatListLiveData.observe(viewLifecycleOwner){ chatList ->
            val data = chatList as ArrayList<Chat>
            adapter.submitList(data)
              binding.offeredChatsRv.adapter = adapter
        }
    }

}