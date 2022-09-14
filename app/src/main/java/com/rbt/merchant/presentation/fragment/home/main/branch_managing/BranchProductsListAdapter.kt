package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.BR
import com.rbt.merchant.databinding.BranchProductsListItemBinding
import com.rbt.merchant.databinding.ProductsOrderDetailsListItemBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

private const val TAG = "ProductsListOrderDetailsAdapter"
class BranchProductsListAdapter: ListAdapter<ProductOrderDetailsModel, BranchProductsListAdapter.ViewHolder>(ProductsModelDiffCallback()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BranchProductsListItemBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), context)
    }

    class ProductsModelDiffCallback : DiffUtil.ItemCallback<ProductOrderDetailsModel>() {
        override fun areItemsTheSame(oldItem: ProductOrderDetailsModel, newItem: ProductOrderDetailsModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: ProductOrderDetailsModel, newItem: ProductOrderDetailsModel): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(binding: BranchProductsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var itemRowBinding: BranchProductsListItemBinding = binding
        @SuppressLint("LongLogTag")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(obj: ProductOrderDetailsModel, context: Context) {
            if (obj.product_old_price == null){
                itemRowBinding.branchOldPriceTv.visibility = View.INVISIBLE
            }else{
                itemRowBinding.branchOldPriceTv.paintFlags = itemRowBinding.branchOldPriceTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
            
        }
    }
}