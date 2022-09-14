package com.rbt.merchant.presentation.fragment.home.main.branch_managing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentBranchesManagementBinding
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.DynamicFragmentAdapter
import com.rbt.merchant.utils.constants.CurrentFragmentConstant


class BranchesManagementFragment : Fragment() {
    private lateinit var binding:FragmentBranchesManagementBinding
    private val viewModel:BranchesManagementViewModel by lazy {
        ViewModelProvider(this)[BranchesManagementViewModel::class.java]
    }
    private lateinit var branchesPagerAdapter : DynamicFragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBranchesManagementBinding.inflate(inflater,container,false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(true)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.branch_managing)
        (activity as MainActivity?)!!.showProfileImage(false)
        (activity as MainActivity?)!!.showListImage(false)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(BranchesManagementFragmentDirections.actionBranchesManagementFragmentToProfileFragment())
            }
        })
        branchesPagerAdapter = DynamicFragmentAdapter(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestBranchesListLiveData.observe(viewLifecycleOwner) { branchNameList ->
            val data = branchNameList as ArrayList<String>
            branchesPagerAdapter.setBranchesList(branchNameList,CurrentFragmentConstant.BRANCH_MANAGEMENT_FRAG)
            binding.branchesViewPager.adapter = branchesPagerAdapter

            TabLayoutMediator(
                binding.branchesTabLayout,
                binding.branchesViewPager
            ) { tab, position ->
                tab.text = branchNameList[position]
            }.attach()
            for (branch in data) {
                val tab = (binding.branchesTabLayout.getChildAt(0) as ViewGroup).getChildAt(data.indexOf(branch))
                val p = tab.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(0, 0, 25, 0)
                tab.requestLayout()
            }
        }
    }

}