package com.rbt.merchant.presentation.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentHomeBinding
import com.rbt.merchant.utils.common.CommonFunction
import com.rbt.merchant.utils.sharedPref


private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: token from pref: ${sharedPref.userTokenPref}")
        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.itemIconTintList = null
        binding.appBarMainLayout.openSideMenuImg.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: $item")
        when (item.itemId) {
            R.id.nav_lang_fragment -> {
                showMenu(binding.navView.findViewById(R.id.nav_lang_fragment), R.menu.language_menu)
            }
            R.id.nav_logout_btn -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        return true
    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val wrapper: Context = ContextThemeWrapper(requireContext(), R.style.PopupMenu)
        val popup = PopupMenu(wrapper, v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5f,
                    resources.displayMetrics
                ).toInt()
                if (item.icon != null) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0)
                    } else {
                        item.icon =
                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                Log.d(TAG, "showMenu: ")
                when (menuItem.itemId) {
                    R.id.jordan_lang -> {
                        CommonFunction.showToast(requireContext(), "Jordan selected")
                        CommonFunction.onClickJordanLang(requireActivity())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    R.id.english_lang -> {
                        CommonFunction.showToast(requireContext(), "English selected")
                        CommonFunction.onClickEnglishLang(requireActivity())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    R.id.india_lang -> {
                        CommonFunction.showToast(requireContext(), "India selected")
                        CommonFunction.onClickIndiaLang(requireActivity())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popup.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            // Show the popup menu.
            popup.show()
        }
    }

}