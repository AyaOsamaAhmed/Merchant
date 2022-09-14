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
        (activity as MainActivity?)!!.showNavDrawer(true)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.product_managing)
        (activity as MainActivity?)!!.showProfileImage(true)
        (activity as MainActivity?)!!.showListImage(true)
        binding.model = arguments?.getParcelable("ProductData")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productPlusImg.setOnClickListener {
            binding.productQuantityNumTxt.text = (binding.productQuantityNumTxt.text.toString().toInt() + 1).toString()
        }
        binding.productMinusImg.setOnClickListener {
            if(binding.productQuantityNumTxt.text.toString().toInt() != 0 ) {
                binding.productQuantityNumTxt.text =
                    (binding.productQuantityNumTxt.text.toString().toInt() - 1).toString()
            }
        }
        binding.activeSwitcherBtn.setOnClickListener {
            if (binding.activeSwitcherBtn.isChecked){
                binding.activeSwitcherBtn.text = context?.getText(R.string.active)
            }else{
                binding.activeSwitcherBtn.text = context?.getText(R.string.not_active)
            }
        }
    }
}