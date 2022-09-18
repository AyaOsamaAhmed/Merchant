package com.rbt.merchant.presentation.fragment.home.main.chat.chat_details


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.databinding.OrdersToolBoxListItemBinding

private const val TAG = "RelatedOrderAdapter"
class RelatedOrderAdapter: ListAdapter<String, RelatedOrderAdapter.ViewHolder>(
    RelatedOrderModelDiffCallback()
) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = OrdersToolBoxListItemBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), context)
    }

    class RelatedOrderModelDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(binding: OrdersToolBoxListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var itemRowBinding: OrdersToolBoxListItemBinding = binding
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(obj: String, context: Context) {
            itemRowBinding.model = obj
            itemRowBinding.executePendingBindings()
            itemRowBinding.relatedComplaintsTxtLabel.setOnClickListener {
                Toast.makeText(context,obj,Toast.LENGTH_LONG).show()
            }
        }
    }
}