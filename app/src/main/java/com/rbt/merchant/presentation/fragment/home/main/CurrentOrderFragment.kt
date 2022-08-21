package com.rbt.merchant.presentation.fragment.home.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentCurrentOrderBinding


class CurrentOrderFragment : Fragment() {
    private lateinit var binding: FragmentCurrentOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentOrderBinding.inflate(inflater,container,false)
        return binding.root
    }

}