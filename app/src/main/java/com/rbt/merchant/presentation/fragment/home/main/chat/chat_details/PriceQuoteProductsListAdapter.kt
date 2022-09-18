package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.BR
import com.rbt.merchant.databinding.BranchProductsListItemBinding
import com.rbt.merchant.databinding.PriceQuoteProductListItemBinding
import com.rbt.merchant.databinding.ProductsOrderDetailsListItemBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel

private const val TAG = "ProductsListOrderDetailsAdapter"
class PriceQuoteProductsListAdapter: ListAdapter<ProductOrderDetailsModel, PriceQuoteProductsListAdapter.ViewHolder>(ProductsModelDiffCallback()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PriceQuoteProductListItemBinding.inflate(layoutInflater, parent, false)
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


    class ViewHolder(binding: PriceQuoteProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var itemRowBinding: PriceQuoteProductListItemBinding = binding
        @SuppressLint("LongLogTag")
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(obj: ProductOrderDetailsModel, context: Context) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
            itemRowBinding.productPlusImg.setOnClickListener {
                itemRowBinding.productQuantityNumTxt.text = (itemRowBinding.productQuantityNumTxt.text.toString().toInt() + 1).toString()
            }
            itemRowBinding.productMinusImg.setOnClickListener {
                if(itemRowBinding.productQuantityNumTxt.text.toString().toInt() != 0 ) {
                    itemRowBinding.productQuantityNumTxt.text =
                        (itemRowBinding.productQuantityNumTxt.text.toString().toInt() - 1).toString()
                }
            }
        }
    }
}