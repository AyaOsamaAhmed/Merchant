package com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentMainOrdersListBinding
import com.rbt.merchant.domain.use_case.ui_models.new_orders.NewOrdersModel
import com.rbt.merchant.presentation.fragment.home.main.orders_screen.new_orders.NewOrdersAdapter
import java.util.ArrayList


class MainOrdersListFragment(private val position:Int) : Fragment() {
    private lateinit var binding: FragmentMainOrdersListBinding
    private val viewModel: MainOrderViewModel by lazy {
        ViewModelProvider(this)[MainOrderViewModel::class.java]
    }
    private lateinit var orderAdapter: NewOrdersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainOrdersListBinding.inflate(inflater,container,false)
        orderAdapter = NewOrdersAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestOrdersListLiveData.observe(viewLifecycleOwner){ orderList ->
            val data = orderList as ArrayList<NewOrdersModel>
            orderAdapter.submitList(data)
            binding.mainOrdersRv.adapter = orderAdapter
        }
    }

}