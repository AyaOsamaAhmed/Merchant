package com.rbt.merchant.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbt.merchant.presentation.fragment.home.main.chat.all_chats.ChatsListFragment

class DynamicFragmentAdapter(fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {
    private lateinit var branchesList : ArrayList<String>

    @JvmName("setBranchesList1")
    fun setBranchesList(branchesList:ArrayList<String>){
        this.branchesList = branchesList
    }
    override fun getItemCount(): Int = branchesList.size

    override fun createFragment(position: Int): Fragment {
        return ChatsListFragment(position)
    }
}