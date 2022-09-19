package com.rbt.merchant.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbt.merchant.presentation.fragment.home.main.branch_managing.BranchManagingFragment
import com.rbt.merchant.presentation.fragment.home.main.chat.all_chats.ChatsListFragment
import com.rbt.merchant.presentation.fragment.home.main.chat.price_quote.CurrentProductListFragment
import com.rbt.merchant.presentation.fragment.home.main.chat.price_quote.CustomProductFragment
import com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders.MainOrdersFragment
import com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders.MainOrdersListFragment
import com.rbt.merchant.utils.constants.CurrentFragmentConstant
import kotlin.properties.Delegates

class DynamicFragmentAdapter(fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {
    private var pagerListSize by Delegates.notNull<Int>()
    private lateinit var currentFragment : String

    @JvmName("setBranchesList1")
    fun setBranchesList(pagerListSize:Int,currentFragment : String){
        this.pagerListSize = pagerListSize
        this.currentFragment = currentFragment
    }
    override fun getItemCount(): Int = pagerListSize

    override fun createFragment(position: Int): Fragment {
        return when(currentFragment){
            CurrentFragmentConstant.CHAT_FRAG -> {
                ChatsListFragment(position)
            }
            CurrentFragmentConstant.MAIN_ORDER_FRAG -> {
                MainOrdersFragment(position)
            }
            CurrentFragmentConstant.MAIN_ORDER_LIST_FRAG -> {
                MainOrdersListFragment(position)
            }
            CurrentFragmentConstant.BRANCH_MANAGEMENT_FRAG -> {
                BranchManagingFragment(position)
            }
            CurrentFragmentConstant.PRICE_QUOTE_FRAG -> {
                when(position){
                    0 -> {
                        CurrentProductListFragment()
                    }
                    1 -> {
                        CustomProductFragment()
                    }
                    else -> {
                        CurrentProductListFragment()
                    }
                }
            }
            else -> {
                Fragment()
            }
        }
    }
}