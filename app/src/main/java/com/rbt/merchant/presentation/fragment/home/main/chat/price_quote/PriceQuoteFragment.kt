package com.rbt.merchant.presentation.fragment.home.main.chat.price_quote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentPriceQuoteBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

private const val TAG = "PriceQuoteFragment"
class PriceQuoteFragment : Fragment() {
    private lateinit var binding:FragmentPriceQuoteBinding
    private val viewModel: PriceQuoteViewModel by lazy {
        ViewModelProvider(this)[PriceQuoteViewModel::class.java]
    }
    private lateinit var productsAdapter: PriceQuoteProductsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPriceQuoteBinding.inflate(inflater,container,false)
        productsAdapter = PriceQuoteProductsListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestProductsListLiveData.observe(viewLifecycleOwner){ productsList ->
            Log.d(TAG, "onViewCreated: productsList: ${productsList.size}")
            val data = productsList as ArrayList<ProductOrderDetailsModel>
            productsAdapter.submitList(data)
            binding.priceQuoteProductsItemsRv.adapter = productsAdapter
        }
        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when(checkedId){
                binding.deliveryBtn.id -> {
                    Toast.makeText(requireContext(),context?.getText(R.string.delivery),Toast.LENGTH_SHORT).show()
                }
                binding.receiptFromTheStoreBtn.id -> {
                    Toast.makeText(requireContext(),context?.getText(R.string.receipt_from_the_store),Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.addProductCardView.setOnClickListener {
            val bottomSheet = PriceQuoteBottomSheetDialogFragment()
            bottomSheet.show(parentFragmentManager,"AddProduct")
        }
    }

}