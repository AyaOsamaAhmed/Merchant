package com.rbt.merchant.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbt.merchant.presentation.fragment.home.main.branch_managing.BranchManagingFragment
import com.rbt.merchant.presentation.fragment.home.main.branch_managing.BranchesManagementFragment
import com.rbt.merchant.presentation.fragment.home.main.chat.all_chats.ChatsListFragment
import com.rbt.merchant.utils.constants.CurrentFragmentConstant

class DynamicFragmentAdapter(fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {
    private lateinit var branchesList : ArrayList<String>
    private lateinit var currentFragment : String

    @JvmName("setBranchesList1")
    fun setBranchesList(branchesList:ArrayList<String>,currentFragment : String){
        this.branchesList = branchesList
        this.currentFragment = currentFragment
    }
    override fun getItemCount(): Int = branchesList.size

    override fun createFragment(position: Int): Fragment {
        return when(currentFragment){
            CurrentFragmentConstant.CHAT_FRAG -> {
                ChatsListFragment(position)
            }
            CurrentFragmentConstant.BRANCH_MANAGEMENT_FRAG -> {
                BranchManagingFragment(position)
            }
            else -> {
                Fragment()
            }
        }
    }
}