package com.rbt.merchant.presentation.fragment.splash_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.databinding.FragmentSplashBinding
import com.rbt.merchant.utils.sharedPref

/**
 * splash screen :
 * Check if user have login already or not
 * if login: open home fragment
 * if not: open phone fragment
*/

private const val TAG = "SplashFragment"
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater, container, false)

        // Handler check if app has user token in shared preference for 4 sec
        /*
            if null : open phone fragment

                - to start sign up process and get new token.

                - send transitionName as extra value to nav action as string from_splash_to_phone_auth

            if not null: open home fragment
         */
        Handler(Looper.getMainLooper()).postDelayed({
            val extras = FragmentNavigatorExtras(binding.logo to "from_splash_to_phone_auth")
            if (sharedPref.userTokenPref.isNullOrEmpty()) {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToPhoneAuthFragment(),
                    extras
                )
            } else {
                Log.d(TAG, "onCreateView: go to home")
               /* findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                )*/
            }
        }, 4000)



        return binding.root
    }

}
