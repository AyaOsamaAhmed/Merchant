package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.merchant.databinding.FragmentBranchManagingBinding


class BranchManagingFragment(private var position:Int) : Fragment() {
    private lateinit var binding: FragmentBranchManagingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBranchManagingBinding.inflate(inflater,container,false)
        return binding.root
    }

}