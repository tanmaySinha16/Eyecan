package com.example.eyecan.utils

import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object Permissions {
    fun hasLocationPermissions(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
}