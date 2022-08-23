package com.rbt.merchant.presentation.fragment.home.main.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.databinding.ChatItemLeftBinding
import com.rbt.merchant.databinding.ChatItemRightBinding
import com.rbt.merchant.databinding.ChatTimeItemBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.MessagesModel


private const val SENDER_LAYOUT = 1
private const val  RECEIVER_LAYOUT = 2
private const val TIME_LAYOUT = 3
private const val TAG = "MessagesAdapter"
class MessagesAdapter: ListAdapter<MessagesModel , RecyclerView.ViewHolder>(MessagesModelDiffCallback()) {
    private lateinit var listener: OnItemClickListener
    private lateinit var bindingSend: ChatItemRightBinding
    private lateinit var bindingReceive: ChatItemLeftBinding
    private lateinit var bindingTime: ChatTimeItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingSend = ChatItemRightBinding.inflate(layoutInflater, parent, false)
        bindingReceive = ChatItemLeftBinding.inflate(layoutInflater, parent, false)
        bindingTime = ChatTimeItemBinding.inflate(layoutInflater, parent, false)
        return when(viewType){
            SENDER_LAYOUT -> {
                 SenderViewHolder(bindingSend)
            }
            RECEIVER_LAYOUT -> {
                 ReceiverViewHolder(bindingReceive)
            }
            TIME_LAYOUT -> {
                 TimeViewHolder(bindingTime)
            }
            else -> {
                Log.d(TAG, "onCreateViewHolder: view from else")
                SenderViewHolder(bindingSend)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when(getItem(position).viewType){
            SENDER_LAYOUT -> {
                return SENDER_LAYOUT
            }
            RECEIVER_LAYOUT -> {
                return RECEIVER_LAYOUT
            }
            TIME_LAYOUT -> {
                return TIME_LAYOUT
            }
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position).viewType){
            SENDER_LAYOUT -> {
                (holder as SenderViewHolder).bind(getItem(position))
            }
            RECEIVER_LAYOUT -> {
                (holder as ReceiverViewHolder).bind(getItem(position))
            }
            TIME_LAYOUT -> {
                (holder as TimeViewHolder).bind(getItem(position))
            }
        }

    }

    class MessagesModelDiffCallback :
        DiffUtil.ItemCallback<MessagesModel>() {
        override fun areItemsTheSame(oldItem: MessagesModel, newItem: MessagesModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: MessagesModel, newItem: MessagesModel): Boolean {
            return oldItem == newItem
        }
    }


    inner class SenderViewHolder(binding: ChatItemRightBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ChatItemRightBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            itemRowBinding.executePendingBindings()
        }
    }
    inner class ReceiverViewHolder(binding: ChatItemLeftBinding) : RecyclerView.ViewHolder(binding.root) {
         var itemRowBinding: ChatItemLeftBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            itemRowBinding.executePendingBindings()
        }
    }
    inner class TimeViewHolder(binding: ChatTimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
         var itemRowBinding: ChatTimeItemBinding = binding
        fun bind(item: MessagesModel) {
            itemRowBinding.model = item
            itemRowBinding.executePendingBindings()
        }
    }
    interface OnItemClickListener{
        fun onClick(item: MessagesModel)
    }
}