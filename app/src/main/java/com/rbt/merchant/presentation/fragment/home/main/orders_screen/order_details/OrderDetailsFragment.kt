package com.rbt.merchant.presentation.fragment.home.main.orders_screen.order_details

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rbt.merchant.databinding.FragmentOrderDetailsBinding
import com.rbt.merchant.domain.use_case.ui_models.chat.Chat
import com.rbt.merchant.domain.use_case.ui_models.order_details.OrderDetailsStatusModel
import com.rbt.merchant.domain.use_case.ui_models.order_details.ProductOrderDetailsModel
import com.rbt.merchant.presentation.ui.MainActivity

class OrderDetailsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var mMap : GoogleMap

    private  val viewModel: OrderDetailsViewModel by lazy {
        ViewModelProvider(this)[OrderDetailsViewModel::class.java]
    }
    private var adapterStatus : OrderDetailsStatusAdapter = OrderDetailsStatusAdapter()
    private var adapterProduct : ProductsListOrderDetailsAdapter = ProductsListOrderDetailsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        (activity as MainActivity?)!!.showNavBottom(true)
        (activity as MainActivity?)!!.showToolBar(false)
        (activity as MainActivity?)!!.showNavDrawer(false)
        (activity as MainActivity?)!!.showFragmentTitle(false, null)
        (activity as MainActivity?)!!.showProfileImage(false)
        val orderId = arguments?.getString("order_id")
        binding.orderNumberTxtOrderDetails.text = orderId.toString()
        adapterStatus.submitStatus(OrderDetailsStatusModel(orderId!!.toInt() ,2,"12:48م"))
        // Initialize map fragment
        val supportMapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment?
        supportMapFragment!!.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.orderDetailsStatusRv.adapter = adapterStatus
        viewModel.requestProductsListLiveData.observe(viewLifecycleOwner){ productsList ->
            val data = productsList as ArrayList<ProductOrderDetailsModel>
            adapterProduct.submitList(data)
            binding.productsItemsRv.adapter = adapterProduct
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Add a marker in Sydney and move the camera
        placeMarkerOnMap(LatLng(21.1235, 57.1256))

        setUpMap()
    }
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            val  service = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!enabled) {
                val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(i)
                enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }
            return
        }
        mMap.isMyLocationEnabled = true

        return
    }


    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}