package com.rbt.merchant.presentation.fragment.home.main.chivalry_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentChivalryRBTBinding
import com.rbt.merchant.presentation.ui.MainActivity


class ChivalryRBTFragment : Fragment() {
    private lateinit var binding: FragmentChivalryRBTBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChivalryRBTBinding.inflate(inflater,container,false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(true)
        (activity as MainActivity?)!!.showNavDrawer(true)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.chivalry_rbt)
        (activity as MainActivity?)!!.showProfileImage(true)
        (activity as MainActivity?)!!.showListImage(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ChivalryRBTFragmentDirections.actionChivalryRBTFragmentToHomeFragment2())
            }
        })
        return binding.root
    }

}