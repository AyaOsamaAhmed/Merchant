package com.rbt.merchant.utils.common

import android.Manifest

object PermissionsRequestCode {
   const val VERIFICATION_CODE = "code"
   const val IMAGE_PATH = "Media/Profile_Image/profile"
   const val GROUP_IMAGE = "/Media/Profile/profile.jpg"
   const val GROUP_IMAGE_MESSAGE = "/Media/Images/"
   const val GROUP_AUDIO_MESSAGE = "/Media/Recording/"
   const val STORAGE_REQUEST_CODE = 1000
   const val LOCATION_REQUEST_CODE = 2000
   const val RECORDING_REQUEST_CODE = 3000
   var RecordAndStoragePermissionList = arrayOf(
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
   )
   var CameraAndStoragePermissionList = arrayOf(
      Manifest.permission.CAMERA,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
   )
}