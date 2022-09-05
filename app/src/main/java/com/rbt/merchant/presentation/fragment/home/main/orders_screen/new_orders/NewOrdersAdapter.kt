package com.rbt.merchant.presentation.fragment.home.main.orders_screen.new_orders

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.R
import com.rbt.merchant.databinding.ItemChatBinding
import com.rbt.merchant.databinding.NewOrderListItemBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.new_orders.NewOrdersModel
import com.rbt.merchant.presentation.fragment.home.main.chat.all_chats.ChatFragmentDirections


private const val TAG = "NewOrdersAdapter"
class NewOrdersAdapter: ListAdapter<NewOrdersModel, NewOrdersAdapter.ViewHolder>(NewOrdersModelDiffCallback()) {
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

    class NewOrdersModelDiffCallback : DiffUtil.ItemCallback<NewOrdersModel>() {
        override fun areItemsTheSame(oldItem: NewOrdersModel, newItem: NewOrdersModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: NewOrdersModel, newItem: NewOrdersModel): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(binding: NewOrderListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var itemRowBinding: NewOrderListItemBinding = binding
        fun bind(newOrder: NewOrdersModel, context: Context) {
            itemRowBinding.clientNameTxtHome.text = newOrder.client_name
            itemRowBinding.orderDateTxtHome.text = newOrder.order_date
            itemRowBinding.orderNumberTxtHome.text = newOrder.order_Id
            itemRowBinding.executePendingBindings()
        }
    }
}