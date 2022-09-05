package com.rbt.merchant.presentation.fragment.home.main.orders_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentOrdersBinding
import com.rbt.merchant.presentation.ui.MainActivity


class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater,container,false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(true)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.current_orders)
        (activity as MainActivity?)!!.showProfileImage(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(OrdersFragmentDirections.actionOrdersFragmentToHomeFragment2())
            }
        })
        return binding.root
    }

}