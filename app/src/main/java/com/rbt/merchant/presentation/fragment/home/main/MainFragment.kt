package com.rbt.merchant.presentation.fragment.home.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentMainBinding
import com.rbt.merchant.utils.common.CommonFunction


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        CommonFunction.openCustomFragment(requireActivity(),binding.mainOperationContainer.id,CurrentOrderFragment())
        binding.bottomNav.selectedItemId = R.id.main_operation_menu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.archive_menu -> {
                    CommonFunction.openCustomFragment(requireActivity(),binding.mainOperationContainer.id,ArchiveFragment())
                }
                R.id.chat_menu -> {
                    CommonFunction.openCustomFragment(requireActivity(),binding.mainOperationContainer.id,ChatClickedFragment())
                }
                R.id.account_menu -> {
                    CommonFunction.openCustomFragment(requireActivity(),binding.mainOperationContainer.id,ProfileFragment())
                }
                R.id.main_operation_menu -> {
                    CommonFunction.openCustomFragment(requireActivity(),binding.mainOperationContainer.id,CurrentOrderFragment())
                }
                R.id.statistic_menu -> {
                    CommonFunction.openCustomFragment(requireActivity(),binding.mainOperationContainer.id,StatisticsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

}