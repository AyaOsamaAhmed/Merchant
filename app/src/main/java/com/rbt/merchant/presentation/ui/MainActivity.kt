package com.rbt.merchant.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.InsetDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.MenuRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rbt.merchant.R
import com.rbt.merchant.databinding.ActivityMainBinding
import com.rbt.merchant.presentation.fragment.home.main.StatisticsFragmentDirections
import com.rbt.merchant.presentation.fragment.home.main.chat.all_chats.ChatFragmentDirections
import com.rbt.merchant.presentation.fragment.home.main.chivalry_screen.ChivalryRBTFragmentDirections
import com.rbt.merchant.presentation.fragment.home.main.home_screen.HomeFragmentDirections
import com.rbt.merchant.presentation.fragment.home.main.orders_screen.main_orders.OrdersFragmentDirections
import com.rbt.merchant.presentation.fragment.home.side_menu.ProfilesSwitcherAdapter
import com.rbt.merchant.utils.common.CommonFunction
import com.rbt.merchant.utils.common.LocaleHelper
import com.rbt.merchant.utils.constants.LanguageConstant
import com.rbt.merchant.utils.sharedPref
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), ShowPinedComponent , NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var profilesAdapter: ProfilesSwitcherAdapter

    private val navController by lazy {
        val navHostFragment = this.supportFragmentManager
            .findFragmentById(R.id.constraint_Fragment_layout) as NavHostFragment
        navHostFragment.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        profilesAdapter = ProfilesSwitcherAdapter()
        if(!sharedPref.appLanguagePref.isNullOrEmpty()) {
            LocaleHelper.applyLanguageContext(
                mContext = this,
                Locale(sharedPref.appLanguagePref!!),
                this
            )
        }

        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.itemIconTintList = null

        binding.openSideMenuImg.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.openProfileImg.setOnClickListener {
            Log.d(TAG, "onCreate: navController.currentDestination ${navController.currentDestination}")
            when(navController.currentDestination?.label){
                "HomeFragment" -> {
                    navController.navigate(HomeFragmentDirections.actionHomeFragment2ToProfileFragment())
                }
                "ChivalryRBTFragment" -> {
                    navController.navigate(ChivalryRBTFragmentDirections.actionChivalryRBTFragmentToProfileFragment())
                }
                "ChatFragment" -> {
                    navController.navigate(ChatFragmentDirections.actionChatFragmentToProfileFragment())
                }
                "StatisticsFragment" -> {
                    navController.navigate(StatisticsFragmentDirections.actionStatisticsFragmentToProfileFragment())
                }
                "OrdersFragment" -> {
                    navController.navigate(OrdersFragmentDirections.actionOrdersFragmentToProfileFragment())
                }
            }

        }
        val profilesList = ArrayList<Pair<String,Int>>()
        profilesList.add(Pair("Store One",R.drawable.ic_rbt_logo))
        profilesList.add(Pair("Store Two",R.drawable.ic_rbt_logo))
        profilesList.add(Pair("Store There",R.drawable.ic_rbt_logo))
        profilesAdapter.setProfilesList(profilesList)
        binding.navView.getHeaderView(0).findViewById<Spinner>(R.id.profiles_switcher_spin).adapter = profilesAdapter
        showNavBottom(false)
        showToolBar(false)
        showNavDrawer(false)
        showFragmentTitle(false,null)
        showProfileImage(false)
        showListImage(false)
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun attachBaseContext(newBase: Context?) {
        Log.d(TAG, "attachBaseContext: in")
        Log.d(TAG, "attachBaseContext: sharedPref.appLanguagePref ${sharedPref.appLanguagePref}")
        if(newBase == null){
            Log.d(TAG, "attachBaseContext: new base context is null ")
        }else {
            if (sharedPref.appLanguagePref.equals(LanguageConstant.JORDAN_LANG)) {
                super.attachBaseContext(
                    LocaleHelper.applyLanguageContext(
                        newBase,
                        Locale(LanguageConstant.JORDAN_LANG),
                        this
                    )
                )
            }else if (sharedPref.appLanguagePref.equals(LanguageConstant.INDIA_LANG)){
                super.attachBaseContext(
                    LocaleHelper.applyLanguageContext(
                        newBase,
                        Locale(LanguageConstant.INDIA_LANG),
                        this
                    )
                )
            }else if (sharedPref.appLanguagePref.equals(LanguageConstant.ENGLISH_LANG)){
                super.attachBaseContext(
                    LocaleHelper.applyLanguageContext(
                        newBase,
                        Locale(LanguageConstant.ENGLISH_LANG),
                        this
                    )
                )
            }else{
                Log.d(TAG, "attachBaseContext: shared pref lang is null ")
                super.attachBaseContext(
                    newBase
                )
            }
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
        val wrapper: Context = ContextThemeWrapper(this, R.style.PopupMenu)
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
                        CommonFunction.showToast(this, "Jordan selected")
                        CommonFunction.onClickJordanLang(this)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    R.id.english_lang -> {
                        CommonFunction.showToast(this, "English selected")
                        CommonFunction.onClickEnglishLang(this)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    R.id.india_lang -> {
                        CommonFunction.showToast(this, "India selected")
                        CommonFunction.onClickIndiaLang(this)
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


    override fun showNavBottom(check: Boolean) {
        if(check)
            binding.bottomNav.visibility = View.VISIBLE
        else
            binding.bottomNav.visibility = View.GONE
    }

    override fun showNavDrawer(check: Boolean) {
        if(check) {
            //binding.appbarLayout.visibility = View.VISIBLE
            binding.navView.visibility = View.VISIBLE
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        }
        else {
          //  binding.appbarLayout.visibility = View.GONE
            binding.navView.visibility = View.GONE
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        }
    }

    override fun showToolBar(check: Boolean) {
        if(check) {
            binding.appbarLayout.visibility = View.VISIBLE
        }
        else {
             binding.appbarLayout.visibility = View.GONE
        }
    }

    override fun showFragmentTitle(check: Boolean,titleResource:Int?) {
        if(check) {
            //binding.appbarLayout.visibility = View.VISIBLE
            binding.fragmentTitle.visibility = View.VISIBLE
            binding.fragmentTitle.text = titleResource?.let { getText(it) }
        }
        else {
            //  binding.appbarLayout.visibility = View.GONE
            binding.fragmentTitle.visibility = View.GONE
        }
    }

    override fun showProfileImage(check: Boolean) {
        if(check) {
            //binding.appbarLayout.visibility = View.VISIBLE
            binding.openProfileImg.visibility = View.VISIBLE
        }
        else {
            //  binding.appbarLayout.visibility = View.GONE
            binding.openProfileImg.visibility = View.GONE
        }
    }

    override fun showListImage(check: Boolean) {
        if(check) {
            binding.openSideMenuImg.visibility = View.VISIBLE
        }
        else {
            binding.openSideMenuImg.visibility = View.GONE
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        CommonFunction.showToast(this,"Profile CLicked")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}