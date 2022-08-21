package com.rbt.merchant.presentation.fragment.home.main


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.R
import com.rbt.merchant.databinding.ItemChatBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat


class ChatAdapter(val onclick: OnChatClicked) :
    ListAdapter<Chat, ChatAdapter.ViewHolder>(ChatModelDiffCallback()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemRowBinding.card.setOnClickListener {
            holder.itemRowBinding.unRead.visibility = View.GONE
            holder.itemRowBinding.lastMessage.setTextColor(context.getColor(R.color.gray))
            holder.itemRowBinding.name.setTextColor(context.getColor(R.color.gray))
            holder.itemRowBinding.time.setTextColor(context.getColor(R.color.gray))
        }
    }

    class ChatModelDiffCallback :
        DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemChatBinding = binding
        fun bind(obj: Chat?) {
            itemRowBinding.model = obj
            itemRowBinding.executePendingBindings()
        }
    }

    interface OnChatClicked {
        fun onClick(id: Int)
    }
}