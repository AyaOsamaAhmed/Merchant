package com.rbt.merchant.presentation.fragment.home.main.orders_screen.new_orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.merchant.databinding.FragmentOrderDetailsBinding
import com.rbt.merchant.presentation.ui.MainActivity

class OrderDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(false)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showFragmentTitle(false, null)
        (activity as MainActivity?)!!.showProfileImage(false)
        val orderId = arguments?.getString("order_id")
        binding.orderNumberTxtOrderDetails.text = orderId.toString()
        return binding.root
    }

}