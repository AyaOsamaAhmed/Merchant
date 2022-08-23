package com.rbt.merchant.presentation.fragment.home.main.chat


import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.R
import com.rbt.merchant.databinding.ItemChatBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.utils.common.CommonFunction

private const val TAG = "ChatAdapter"

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ViewHolder>(ChatModelDiffCallback()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), context)
    }

    class ChatModelDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        private var itemRowBinding: ItemChatBinding = binding

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(obj: Chat, context: Context) {
            Log.d(TAG, "bind: chat: ${obj.time}")
            Log.d(TAG, "bind: chat: ${obj.title}")
            itemRowBinding.model = obj
            itemRowBinding.executePendingBindings()
            itemRowBinding.card.setOnClickListener {
                val navController: NavController?
                itemRowBinding.unRead.visibility = View.GONE
                itemRowBinding.lastMessage.setTextColor(context.getColor(R.color.gray))
                itemRowBinding.name.setTextColor(context.getColor(R.color.gray))
                itemRowBinding.time.setTextColor(context.getColor(R.color.gray))
                navController = Navigation.findNavController(itemView)
                navController.navigate(ChatFragmentDirections.actionChatFragmentToChatDetailsFragment(obj))
            }
        }
    }
}