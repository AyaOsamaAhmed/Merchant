package com.rbt.merchant.presentation.fragment.home.main

import android.Manifest
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rbt.merchant.databinding.FragmentCurrentOrderBinding
import com.rbt.merchant.presentation.ui.MainActivity
import com.rbt.merchant.utils.common.Permissions
import com.rbt.merchant.utils.permissionRequestList
import java.util.*

private const val TAG = "CurrentOrderFragment"

class CurrentOrderFragment : Fragment() {
    private lateinit var binding: FragmentCurrentOrderBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentOrderBinding.inflate(inflater, container, false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showNavDrawer(true)
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
        if (Permissions.isLocationOk(requireContext())) {
            Log.d(TAG, "onCreateView: Permissions.isLocationOk(requireContext())")
            getCurrentLocation()
        } else {
            fetchLocationPermission()
            Log.d(TAG, "onCreateView: ")
        }
        return binding.root
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