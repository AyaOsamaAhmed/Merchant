package com.rbt.merchant.presentation.fragment.home.main.chat.price_quote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentPriceQuoteBottomSheetDialogBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel
import com.rbt.merchant.utils.DynamicFragmentAdapter
import com.rbt.merchant.utils.constants.CurrentFragmentConstant


class PriceQuoteBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPriceQuoteBottomSheetDialogBinding
    private lateinit var branchesPagerAdapter : DynamicFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPriceQuoteBottomSheetDialogBinding.inflate(inflater,container,false)
        branchesPagerAdapter = DynamicFragmentAdapter(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        branchesPagerAdapter.setBranchesList(2, CurrentFragmentConstant.PRICE_QUOTE_FRAG)
        binding.priceQuoteViewPager.adapter = branchesPagerAdapter
        TabLayoutMediator(binding.priceQuoteTabDialog, binding.priceQuoteViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = context?.getText(R.string.current_product)
                }
                1 -> {
                    tab.text = context?.getText(R.string.custom_product)
                }
            }
        }.attach()
    }

}