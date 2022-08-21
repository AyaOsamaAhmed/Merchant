package com.rbt.merchant.presentation.fragment.home.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rbt.merchant.databinding.FragmentChatBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat


class ChatFragment : Fragment(),ChatAdapter.OnChatClicked {
    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel : ChatViewModel
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChatAdapter(this)
        viewModel.requestChatListLiveData.observe(viewLifecycleOwner){ chatList ->
            val data = chatList as ArrayList<Chat>
            adapter.submitList(data)
            binding.offeredChatsRv.adapter = adapter
        }
    }

    override fun onClick(id: Int) {
        TODO("Not yet implemented")
    }

}