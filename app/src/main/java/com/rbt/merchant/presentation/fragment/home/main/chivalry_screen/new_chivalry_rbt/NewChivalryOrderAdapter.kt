package com.rbt.merchant.presentation.fragment.home.main.chivalry_screen.new_chivalry_rbt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.databinding.NewOrderListItemBinding
import com.rbt.merchant.domain.use_case.ui_models.new_chivalry_rbt.NewChivalryRbtModel

private const val TAG = "NewChivalryOrderAdapter"
class NewChivalryOrderAdapter : ListAdapter<NewChivalryRbtModel, NewChivalryOrderAdapter.ViewHolder>(NewChivalryRbtModelDiffCallback()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewOrderListItemBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), context)
    }

    class NewChivalryRbtModelDiffCallback : DiffUtil.ItemCallback<NewChivalryRbtModel>() {
        override fun areItemsTheSame(oldItem: NewChivalryRbtModel, newItem: NewChivalryRbtModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: NewChivalryRbtModel, newItem: NewChivalryRbtModel): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(binding: NewOrderListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var itemRowBinding: NewOrderListItemBinding = binding
        fun bind(newOrder: NewChivalryRbtModel, context: Context) {
            itemRowBinding.clientNameTxtHome.text = newOrder.client_name
            itemRowBinding.orderDateTxtHome.text = newOrder.order_date
            itemRowBinding.orderNumberTxtHome.text = newOrder.order_Id
            itemRowBinding.executePendingBindings()
        }
    }
}