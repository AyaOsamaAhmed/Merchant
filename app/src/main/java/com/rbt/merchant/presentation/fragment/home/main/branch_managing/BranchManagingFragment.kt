package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentBranchManagingBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

private const val TAG = "BranchManagingFragment"
class BranchManagingFragment(private var position:Int) : Fragment() {
    private lateinit var binding: FragmentBranchManagingBinding
    private val viewModel: BranchManagingViewModel by lazy {
        ViewModelProvider(this)[BranchManagingViewModel::class.java]
    }
    private lateinit var branchProductsListAdapter: BranchProductsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBranchManagingBinding.inflate(inflater,container,false)
        branchProductsListAdapter = BranchProductsListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestProductsListLiveData.observe(viewLifecycleOwner){ productsList ->
            val data = productsList as ArrayList<ProductOrderDetailsModel>
            branchProductsListAdapter.submitList(data)
            val layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            binding.branchProductsItemsRv.layoutManager = layoutManager
            binding.branchProductsItemsRv.adapter = branchProductsListAdapter
        }
        if (!binding.activeSwitcherBtn.isChecked){
            binding.activeSwitcherBtn.text = context?.getText(R.string.not_effective)
        }
        binding.activeSwitcherBtn.setOnClickListener {
            if (binding.activeSwitcherBtn.isChecked){
                binding.activeSwitcherBtn.text = context?.getText(R.string.effective)
            }else{
                binding.activeSwitcherBtn.text = context?.getText(R.string.not_effective)
            }
        }

    }

}