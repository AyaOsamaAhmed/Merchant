package com.rbt.merchant.presentation.fragment.home.main.orders_screen.order_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rbt.merchant.databinding.FragmentOrderDetailsBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.order_details.OrderDetailsStatusModel
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel
import com.rbt.merchant.presentation.ui.MainActivity

class OrderDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private  val viewModel: OrderDetailsViewModel by lazy {
        ViewModelProvider(this)[OrderDetailsViewModel::class.java]
    }
    private var adapterStatus : OrderDetailsStatusAdapter = OrderDetailsStatusAdapter()
    private var adapterProduct : ProductsListOrderDetailsAdapter = ProductsListOrderDetailsAdapter()
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
        adapterStatus.submitStatus(OrderDetailsStatusModel(orderId!!.toInt() ,2,"12:48م"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.orderDetailsStatusRv.adapter = adapterStatus
        viewModel.requestProductsListLiveData.observe(viewLifecycleOwner){ productsList ->
            val data = productsList as ArrayList<ProductOrderDetailsModel>
            adapterProduct.submitList(data)
            binding.productsItemsRv.adapter = adapterProduct
        }
    }

}