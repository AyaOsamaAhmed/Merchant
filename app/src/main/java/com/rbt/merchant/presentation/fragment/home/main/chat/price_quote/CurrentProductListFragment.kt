package com.rbt.merchant.presentation.fragment.home.main.chat.price_quote

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentCurrentProductListBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

private const val TAG = "CurrentProductListFragment"
class CurrentProductListFragment : Fragment(), PriceQuoteAddProductListAdapter.OnItemClickListener  {
    private lateinit var binding: FragmentCurrentProductListBinding
    private val viewModel: PriceQuoteViewModel by lazy {
        ViewModelProvider(requireActivity())[PriceQuoteViewModel::class.java]
    }
    private lateinit var productsAdapter: PriceQuoteAddProductListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentCurrentProductListBinding.inflate(inflater,container,false)
        productsAdapter = PriceQuoteAddProductListAdapter(this)
        return binding.root
    }

    @SuppressLint("LongLogTag", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestAddProductsListLiveData.observe(viewLifecycleOwner){ productsList ->
            Log.d(TAG, "onViewCreated: productsList: ${productsList.size}")
            val data = productsList as ArrayList<ProductOrderDetailsModel>
            productsAdapter.submitList(data)
            productsAdapter.notifyDataSetChanged()
            binding.currentProductAtPriceQuoteRv.adapter = productsAdapter
        }
    }

    @SuppressLint("LongLogTag")
    override fun onClick(product:ProductOrderDetailsModel) {
          viewModel.addProduct(product)
        Log.d(TAG, "onClick: add product clicked ")
    }
}