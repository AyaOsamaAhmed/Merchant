package com.rbt.merchant.presentation.fragment.home.main.chat.price_quote

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.rbt.merchant.R
import com.rbt.merchant.databinding.FragmentCustomProductBinding
import com.rbt.merchant.utils.common.Permissions
import com.rbt.merchant.utils.common.PermissionsRequestCode
import com.rbt.merchant.utils.permissionRequestList
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import java.net.URI
import java.util.ArrayList

private const val TAG = "CustomProductFragment"
class CustomProductFragment : Fragment() {
    private lateinit var binding: FragmentCustomProductBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isCameraPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isReadPermissionGranted = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomProductBinding.inflate(inflater,container,false)
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                Log.d(TAG, "onCreateView: permissionLauncher ")
                isCameraPermissionGranted =
                    permissions[Manifest.permission.CAMERA] ?: isCameraPermissionGranted
                isWritePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: isWritePermissionGranted
                isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: isReadPermissionGranted
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val list: ArrayList<ImageFile> =
                    result.data?.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE)!!
                Log.d(TAG, "onViewCreated: image selected: ${list.size}")
                for(img in list){
                    binding.productSelectedImage.visibility = View.VISIBLE
                   // binding.productImageTextField.editText?.text = "${img.name}"
                   binding.productSelectedImage.setImageURI(Uri.parse(img.path))
                }
            }
        }
        binding.productImageTextField.editText?.setOnClickListener {
            Log.d(TAG, "onViewCreated: productImageTextField: clicked")
            if (Permissions.isCameraAndStorageOk(context, PermissionsRequestCode.CameraAndStoragePermissionList)) {
                if (Permissions.isCameraOk(requireContext()) && Permissions.isStorageOk(requireContext())) {
                    val intent1 = Intent(context, ImagePickActivity::class.java)
                    intent1.putExtra(ImagePickActivity.IS_NEED_CAMERA, true)
                    intent1.putExtra(Constant.MAX_NUMBER, 1)
                    resultLauncher.launch(intent1)
                } else {
                    Log.d(TAG, "onViewCreated: else ")
                    if (!isCameraPermissionGranted)
                        fetchCameraPermission()
                    else if (!isReadPermissionGranted || !isWritePermissionGranted)
                        fetchStoragePermission()
                }
            } else {
                AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setTitle("App Permission")
                    .setMessage("This app need to access some permissions")
                    .setPositiveButton("ok") { dialog, _ ->
                        Permissions.requestCameraAndStorage(requireActivity(), PermissionsRequestCode.CameraAndStoragePermissionList)
                        dialog.cancel()
                    }
                    .setNegativeButton("cancel") { dialog, _ ->
                        dialog.cancel()
                    }.create().show()
            }
        }
    }

    private fun fetchCameraPermission() {
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (!isCameraPermissionGranted) {
            Permissions.requestCamera()
        }
        if (permissionRequestList.isNotEmpty()) {
            permissionLauncher.launch(permissionRequestList.toTypedArray())
        }
    }
    private fun fetchStoragePermission() {
        isReadPermissionGranted = Permissions.isStorageOk(requireContext())
        isWritePermissionGranted = Permissions.isStorageOk(requireContext())
        if (!isReadPermissionGranted && !isWritePermissionGranted) {
            Permissions.requestStorage()
        }
        if (permissionRequestList.isNotEmpty()) {
            permissionLauncher.launch(permissionRequestList.toTypedArray())
        }
    }

}