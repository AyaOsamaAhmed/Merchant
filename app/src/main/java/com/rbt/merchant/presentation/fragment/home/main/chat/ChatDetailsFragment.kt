package com.rbt.merchant.presentation.fragment.home.main.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbt.merchant.databinding.FragmentChatDetailsBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.chat.MessagesModel
import com.rbt.merchant.presentation.ui.MainActivity

private const val SENDER_LAYOUT = 1
private const val RECEIVER_LAYOUT = 2
private const val TIME_LAYOUT = 3
private const val TAG = "ChatDetailsFragment"

class ChatDetailsFragment : Fragment() {

    private lateinit var binding: FragmentChatDetailsBinding
    private var messagesList = ArrayList<MessagesModel>()
    private lateinit var adapter: MessagesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailsBinding.inflate(inflater, container, false)
        (activity as MainActivity?)!!.showNavBottom(false)
        (activity as MainActivity?)!!.showNavDrawer(false)
        val chat = arguments?.getParcelable<Chat>("Chat")
        binding.model = chat
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesList.add(MessagesModel(viewType = TIME_LAYOUT, timeStamp = "Sun,3-2-2021"))
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:00pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:01pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:10pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:30pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = SENDER_LAYOUT,
                message = "This is Sender",
                messageTime = "02:40pm"
            )
        )
        messagesList.add(
            MessagesModel(
                viewType = RECEIVER_LAYOUT,
                message = "This is Receiver",
                messageTime = "02:57pm"
            )
        )
        binding.addNewMessageImg.setOnClickListener {
            val message = binding.messageEditText.text
            if (!message.isNullOrEmpty()) {
                Log.d(TAG, "onViewCreated: $message")
                messagesList.add(MessagesModel(viewType = 1, message = "This is Sender"))
            }
        }
        adapter = MessagesAdapter()
        adapter.submitList(messagesList)
        binding.messagesList.layoutManager = LinearLayoutManager(context)
        binding.messagesList.adapter = adapter
        binding.messagesList.scrollState
        adapter.notifyDataSetChanged()
    }

}