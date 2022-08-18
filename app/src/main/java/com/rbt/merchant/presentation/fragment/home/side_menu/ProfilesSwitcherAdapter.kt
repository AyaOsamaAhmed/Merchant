package com.rbt.merchant.presentation.fragment.home.side_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.rbt.merchant.databinding.ProfilesSwitcherListItemBinding


private const val TAG = "ProfilesSwitcherAdapter"

class ProfilesSwitcherAdapter : BaseAdapter() {
    private lateinit var profilesList: ArrayList<Pair<String, Int>>
    private lateinit var binding: ProfilesSwitcherListItemBinding
    override fun getCount(): Int {
        return profilesList.size
    }

    override fun getItem(i: Int): Pair<String, Int> {
        return profilesList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(viewGroup!!.context)
        binding = ProfilesSwitcherListItemBinding.inflate(layoutInflater, viewGroup, false)
        binding.profileImg.setImageResource(profilesList[i].second)
        binding.profileNameTv.text = profilesList[i].first
        return binding.root
    }

    fun setProfilesList(profilesList: ArrayList<Pair<String, Int>>) {
        this.profilesList = profilesList
    }
}