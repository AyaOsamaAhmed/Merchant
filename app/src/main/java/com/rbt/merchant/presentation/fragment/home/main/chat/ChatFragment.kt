package com.rbt.merchant.presentation.fragment.home.main.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.databinding.FragmentChatBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.presentation.ui.MainActivity


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel : ChatViewModel
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showNavDrawer(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ChatFragmentDirections.actionChatFragmentToCurrentOrderFragment())
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChatAdapter()
        viewModel.requestChatListLiveData.observe(viewLifecycleOwner){ chatList ->
            val data = chatList as ArrayList<Chat>
            adapter.submitList(data)
            binding.offeredChatsRv.adapter = adapter
        }
    }

}