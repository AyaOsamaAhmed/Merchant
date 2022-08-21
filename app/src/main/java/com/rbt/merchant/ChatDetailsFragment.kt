package com.rbt.merchant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.merchant.databinding.FragmentChatDetailsBinding


class ChatDetailsFragment : Fragment() {

    private lateinit var binding: FragmentChatDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentChatDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}