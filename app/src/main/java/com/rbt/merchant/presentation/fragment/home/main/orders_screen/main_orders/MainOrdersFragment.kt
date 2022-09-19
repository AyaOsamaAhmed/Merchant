package com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentMainOrdersBinding
import com.rbt.merchant.utils.DynamicFragmentAdapter
import com.rbt.merchant.utils.constants.CurrentFragmentConstant


class MainOrdersFragment(private val position:Int) : Fragment() {
    private lateinit var binding: FragmentMainOrdersBinding
    private lateinit var branchesPagerAdapter : DynamicFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainOrdersBinding.inflate(inflater,container,false)
        branchesPagerAdapter = DynamicFragmentAdapter(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        branchesPagerAdapter.setBranchesList(2, CurrentFragmentConstant.MAIN_ORDER_LIST_FRAG)
        binding.mainOrderViewPager.adapter = branchesPagerAdapter
        TabLayoutMediator(binding.mainOrderTabDialog, binding.mainOrderViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = context?.getText(R.string.current_orders_tab)
                }
                1 -> {
                    tab.text = context?.getText(R.string.finished_orders)
                }
            }
        }.attach()
    }
}