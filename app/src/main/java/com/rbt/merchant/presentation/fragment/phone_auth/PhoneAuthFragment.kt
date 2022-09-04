package com.rbt.merchant.presentation.fragment.phone_auth


import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.databinding.FragmentPhoneAuthBinding
import com.rbt.merchant.databinding.FragmentSplashBinding
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.common.CommonFunction

private const val TAG = "PhoneAuthFragment"

/**
 * the user enter his phone number
 * check entered number validation
 * if otp code sent successfully: open otp verification fragment
 * if fail: alert user to enter other phone number
*/
class PhoneAuthFragment : Fragment() {

    private lateinit var binding: FragmentPhoneAuthBinding
    // create an instance by lazy initialization from view model
    private val viewModel: PhoneAuthViewModel by lazy {
        ViewModelProvider(this)[PhoneAuthViewModel::class.java]
    }
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // connect PhoneAuthFragment with its xml using binding tool
        binding = FragmentPhoneAuthBinding.inflate(inflater, container, false)
        binding.phoneAuthViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // receive transition animation to move as the same in splash screen logo image
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        // set this animation when enter ot return from fragment
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        // start observe on variables in viewModel
        startObserve()

    }

    private fun startObserve(){
        //observe on phone variable response
        viewModel.phone.observe(viewLifecycleOwner) { number ->
            phoneNumber = number
        }
        //observe on authResponse variable response
        viewModel.authResponse.observe(viewLifecycleOwner){ response ->
            Log.d(TAG, "onViewCreated: auth response: $response")
            // check if data is null that means there is a problem with phone number so we need to
            // toast a message return from response to user
            // else
            // otp sent to phone number successfully -> open otp verification fragment
            if(response.data==null) {
                CommonFunction.showToast(requireContext(),response.message.toString())
            }else{
                Log.d(TAG, "onViewCreated: auth response otp: ${response.data.otp}")
                CommonFunction.showToast(requireContext(),"otp sent successfully")
                findNavController().navigate(PhoneAuthFragmentDirections.actionPhoneAuthFragmentToOtpVerificationFragment(phoneNumber))
            }
        }
    }

}