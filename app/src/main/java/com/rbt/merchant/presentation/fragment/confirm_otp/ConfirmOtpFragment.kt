package com.rbt.merchant.presentation.fragment.confirm_otp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rbt.merchant.databinding.FragmentConfirmOtpBinding
import com.rbt.merchant.utils.common.CommonFunction
import com.rbt.merchant.utils.sharedPref

/**
 * the user enter the otp that received to the previous phone number
 * check entered otp code validation
 * if it is a valid otp, open home screen
 * if not valid otp, alert user by otp api response message
 *
 * if otp code sent successfully: open otp verification fragment
 */

private const val TAG = "ConfirmOtpFragment"
class ConfirmOtpFragment : Fragment() {
    private lateinit var binding:FragmentConfirmOtpBinding
    // create an instance by lazy initialization from view model
    private val viewModel: ConfirmOtpViewModel by lazy {
        ViewModelProvider(this)[ConfirmOtpViewModel::class.java]
    }
    private lateinit var otp: String
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // connect ConfirmOtpFragment with its xml using binding tool
        binding = FragmentConfirmOtpBinding.inflate(inflater, container, false)
        binding.confirmOtpViewModel = viewModel
        // set the value of phoneNumber from args
        phoneNumber = ConfirmOtpFragmentArgs.fromBundle(
            requireArguments()
        ).phoneNumber
        // set the value of phoneNumber to phoneNumber on view model
        viewModel.phoneNumber.value = phoneNumber
        Log.d(TAG, "onCreateView: phone number from args: $phoneNumber")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // start observe on variables in viewModel
        startObserve()
    }

    private fun startObserve(){
        //observe on phone variable response
        viewModel.otp.observe(viewLifecycleOwner) { otp ->
            this.otp = otp
        }
        //observe on confirmOtpResponse variable response
        viewModel.confirmOtpResponse.observe(viewLifecycleOwner){ response ->
            Log.d(TAG, "onViewCreated: auth response: $response")
            // check if data is null that means there is a problem with otp so we need to
            // toast a message return from response to user
            // else
            // otp is the correct otp  -> open home fragment
            if(response.data==null) {
                CommonFunction.showToast(requireContext(),response.message.toString())
            }else{
                CommonFunction.showToast(requireContext(),"otp match successfully")
                Log.d(TAG, "onViewCreated: auth response token: ${response.data.session}")
                findNavController().navigate(ConfirmOtpFragmentDirections.actionOtpVerificationFragmentToHomeFragment())
                sharedPref.userTokenPref = response.data.session
            }
        }
        //observe on resendOtpResponse variable response
        viewModel.resendOtpResponse.observe(viewLifecycleOwner){ response ->
            Log.d(TAG, "onViewCreated: auth response: $response")
            // clear pinView text
            binding.squareField.text!!.clear()
            // check if data is null that means there is a problem with phone number so we need to
            // toast a message return from response to user
            // else
            // otp sent to phone number successfully -> alert user by toast message that otp sent successfully
            if(response.data==null) {
                CommonFunction.showToast(requireContext(),response.message.toString())
            }else{
                CommonFunction.showToast(requireContext(),"otp sent successfully")
            }
        }
    }
}