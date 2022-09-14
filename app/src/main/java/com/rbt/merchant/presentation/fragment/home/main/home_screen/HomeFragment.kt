package com.rbt.merchant.presentation.fragment.home.main.home_screen

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentHomeBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.new_chivalry_rbt.NewChivalryRbtModel
import com.rbt.merchant.domain.use_case.ui_models.new_orders.NewOrdersModel
import com.rbt.merchant.presentation.fragment.home.main.chat.all_chats.ChatAdapter
import com.rbt.merchant.presentation.fragment.home.main.chivalry_screen.new_chivalry_rbt.NewChivalryOrderAdapter
import com.rbt.merchant.presentation.fragment.home.main.orders_screen.new_orders.NewOrdersAdapter
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.common.Permissions
import com.rbt.merchant.utils.permissionRequestList
import java.util.*

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    // access location
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // new Chats Adapter
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var orderAdapter: NewOrdersAdapter
    private lateinit var chivalryOrderAdapter: NewChivalryOrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(true)
        (activity as MainActivity?)!!.showNavDrawer(true)
        (activity as MainActivity?)!!.showFragmentTitle(true, R.string.home)
        (activity as MainActivity?)!!.showProfileImage(true)
        (activity as MainActivity?)!!.showListImage(true)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                Log.d(TAG, "onCreateView: permissionLauncher ")
                isLocationPermissionGranted =
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION]
                        ?: isLocationPermissionGranted
                if (isLocationPermissionGranted) {
                    getCurrentLocation()
                }
            }
        getCurrentLocation()
        chatAdapter = ChatAdapter()
        orderAdapter = NewOrdersAdapter()
        chivalryOrderAdapter = NewChivalryOrderAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestChatListLiveData.observe(viewLifecycleOwner){ chatList ->
            val data = chatList as ArrayList<Chat>
            chatAdapter.submitList(data)
            binding.newChatsRvHome.adapter = chatAdapter
        }
        viewModel.requestOrdersListLiveData.observe(viewLifecycleOwner){ orderList ->
            val data = orderList as ArrayList<NewOrdersModel>
            orderAdapter.submitList(data)
            binding.newOrdersRvHome.adapter = orderAdapter
        }
        viewModel.requestChivalryRbtListLiveData.observe(viewLifecycleOwner){ chivalryList ->
            val data = chivalryList as ArrayList<NewChivalryRbtModel>
            chivalryOrderAdapter.submitList(data)
            binding.newNakhwetRbtRvHome.adapter = chivalryOrderAdapter
        }
    }
    private fun getCurrentLocation() {
        if (checkLocationPermission()) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { location ->
                val geocoder =
                    Geocoder(requireContext(), Locale.ENGLISH)
                val address = geocoder.getFromLocation(
                    location.result.latitude,
                    location.result.longitude,
                    1
                )
                Log.d(
                    TAG,
                    "getCurrentLocation: latitude,longitude: ${address[0].latitude},${address[0].longitude}"
                )
                Log.d(TAG, "getCurrentLocation: latitude: ${address[0].latitude}")
                Log.d(TAG, "getCurrentLocation: longitude: ${address[0].longitude}")
            }
        }else {
            AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("App Permission")
                .setMessage("This app need to access your current location")
                .setPositiveButton("ok"){ dialog, which ->
                    fetchLocationPermission()
                    dialog.cancel()
                }
                .setNegativeButton("cancel"){ dialog, which ->
                    dialog.cancel()
                }
                .create()
                .show()

            Log.d(TAG, "onCreateView: ")
        }
    }

    private fun checkLocationPermission(): Boolean {
        return Permissions.isLocationOk(requireContext())
    }

    private fun fetchLocationPermission() {
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!isLocationPermissionGranted) {
            Permissions.requestLocation()
        }
        if (permissionRequestList.isNotEmpty()) {
            permissionLauncher.launch(permissionRequestList.toTypedArray())
        }
    }

}