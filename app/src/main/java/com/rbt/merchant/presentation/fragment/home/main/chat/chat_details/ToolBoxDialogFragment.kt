package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.rbt.merchant.databinding.ChatToolBoxLayoutBinding


class ToolBoxDialogFragment : Fragment() {
    private lateinit var binding: ChatToolBoxLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatToolBoxLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}