package com.rbt.merchant.presentation.fragment.home.main.chat.all_chats

import android.R.attr.contentInsetStart
import android.R.attr.mode
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.*
import com.google.android.material.tabs.TabLayout.GRAVITY_START
import com.google.android.material.tabs.TabLayoutMediator
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentChatBinding
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.DynamicFragmentAdapter


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel : ChatViewModel
    //private lateinit var adapter: ChatAdapter
    private lateinit var branchesPagerAdapter : DynamicFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(true)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.offered_chats)
        (activity as MainActivity?)!!.showProfileImage(true)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ChatFragmentDirections.actionChatFragmentToHomeFragment2())
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        branchesPagerAdapter = DynamicFragmentAdapter(requireActivity())
        viewModel.requestBranchesListLiveData.observe(viewLifecycleOwner) { branchNameList ->
            val data = branchNameList as ArrayList<String>
            branchesPagerAdapter.setBranchesList(branchNameList)
            binding.branchesViewPager.adapter = branchesPagerAdapter

            TabLayoutMediator(
                binding.branchesTabLayout,
                binding.branchesViewPager
            ) { tab, position ->
                tab.text = branchNameList[position]
            }.attach()
            for (branch in data) {
                val tab = (binding.branchesTabLayout.getChildAt(0) as ViewGroup).getChildAt(data.indexOf(branch))
                val p = tab.layoutParams as MarginLayoutParams
                p.setMargins(0, 0, 25, 0)
                tab.requestLayout()
            }
        }
    }

}