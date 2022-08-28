package com.rbt.merchant.utils.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rbt.merchant.utils.permissionRequestList


object Permissions {

    fun isStorageOk(context: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestStorage() {
        permissionRequestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionRequestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun isLocationOk(context: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocation() {
        permissionRequestList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissionRequestList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun isRecordingOk(context: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestRecording() {
        permissionRequestList.add(Manifest.permission.RECORD_AUDIO)
    }

    fun isCameraOk(context: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestCamera() {
        permissionRequestList.add(Manifest.permission.CAMERA)
    }

    fun requestRecordingAndStorage(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, 1)
    }


    fun isRecordingAndStorageOk(context: Context?, permissions: Array<String>): Boolean {
        if (context != null && permissions.isNotEmpty()) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    return true
                }
            }
        }
        return false
    }

    fun requestCameraAndStorage(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, 1)
    }

    fun isCameraAndStorageOk(context: Context?, permissions: Array<String>): Boolean {
        if (context != null && permissions.isNotEmpty()) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    return true
                }
            }
        }
        return false
    }
}