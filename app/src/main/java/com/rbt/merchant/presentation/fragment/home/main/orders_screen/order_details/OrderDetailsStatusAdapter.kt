
package com.rbt.merchant.presentation.fragment.home.main.orders_screen.order_details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rbt.merchant.BR
import com.rbt.merchant.R
import com.rbt.merchant.databinding.ItemOrderDetailsStatusBinding
import com.rbt.merchant.domain.use_case.ui_models.order_details.OrderDetailsStatusModel

class OrderDetailsStatusAdapter ():
    RecyclerView.Adapter<OrderDetailsStatusAdapter.ViewHolder>()  {

    private val models : ArrayList<String> = arrayListOf()
    private lateinit var status : OrderDetailsStatusModel
private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding: ItemOrderDetailsStatusBinding = ItemOrderDetailsStatusBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        models.add(context.getString(R.string.confirm_order))
        models.add(context.getString(R.string.prepare_order))
        models.add(context.getString(R.string.delivery_order))
        models.add(context.getString(R.string.done_order))
        val model = models[position]
          holder.bind(model)


        if(status.status!! > position){
            holder.itemRowBinding.icon.setImageDrawable(context.getDrawable(R.drawable.ic_done))
            holder.itemRowBinding.title.setTextColor(context.getColor(R.color.green))
            holder.itemRowBinding.time.text = status.time
            holder.itemRowBinding.time.visibility = View.VISIBLE

        }else if ( status.status == position){
            holder.itemRowBinding.icon.setImageDrawable(context.getDrawable(R.drawable.ic_progress))
            holder.itemRowBinding.title.setTextColor(context.getColor(R.color.yellow))
            holder.itemRowBinding.time.text = status.time
            holder.itemRowBinding.time.visibility = View.VISIBLE
        }

        if(position == 3){
            holder.itemRowBinding.line.visibility = View.GONE
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun submitStatus (statusItem: OrderDetailsStatusModel){
        status = statusItem
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int  = 4


    class ViewHolder(binding: ItemOrderDetailsStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemOrderDetailsStatusBinding = binding
        fun bind(obj: Any?) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
        }
    }

}