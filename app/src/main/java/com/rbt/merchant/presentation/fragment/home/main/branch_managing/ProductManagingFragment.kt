package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentProductManagingBinding
import com.rbt.merchant.presentation.ui.MainActivity


class ProductManagingFragment : Fragment() {
    private lateinit var binding: FragmentProductManagingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductManagingBinding.inflate(inflater,container,false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(true)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.product_managing)
        (activity as MainActivity?)!!.showProfileImage(false)
        (activity as MainActivity?)!!.showListImage(false)
        return binding.root
    }

}