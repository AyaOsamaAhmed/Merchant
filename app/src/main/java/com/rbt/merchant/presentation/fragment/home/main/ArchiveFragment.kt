package com.rbt.merchant.presentation.fragment.home.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentArchiveBinding
import com.rbt.merchant.presentation.fragment.home.main.chat.ChatFragmentDirections
import com.rbt.merchant.presentation.ui.MainActivity


class ArchiveFragment : Fragment() {
    private lateinit var binding: FragmentArchiveBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArchiveBinding.inflate(inflater,container,false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showNavDrawer(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ArchiveFragmentDirections.actionArchiveFragmentToCurrentOrderFragment())
            }
        })
        return binding.root
    }

}