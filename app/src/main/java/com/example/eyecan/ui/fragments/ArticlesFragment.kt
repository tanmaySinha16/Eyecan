package com.example.eyecan.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eyecan.R
import com.example.eyecan.utils.Constants.PDF_REQUEST_CODE
import com.example.eyecan.utils.Constants.REQUEST_CODE_STORAGE_PERMISSION
import com.example.eyecan.utils.Permissions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class ArticlesFragment:Fragment(R.layout.fragment_articles),EasyPermissions.PermissionCallbacks {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        view.findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_articlesFragment_to_readingFragment)
        }
    }

    private fun requestPermissions() {
        if(Permissions.hasLocationPermissions(requireContext())) {
            return
        }

            EasyPermissions.requestPermissions(
                this,
                "You need to accept storage permissions to use this app.",
                REQUEST_CODE_STORAGE_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )


    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}