package com.example.gpuimageplusmodule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gpuimageplusmodule.databinding.ActivityMainBinding
import com.example.gpuimageplusmodule.lib.MainActivityNew
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.intiLib.setOnClickListener {

            if (EasyPermissions.hasPermissions(this@MainActivity, *Utils.cameraPermissionPass)) {
                openNewScreen()
                Log.d("myPermission", "hasPermissions allow")
            } else {
                EasyPermissions.requestPermissions(
                    this@MainActivity, "Please allow permissions to proceed further",
                    Utils.REQUEST_CAMERA_IMAGE, *Utils.cameraPermissionPass
                )
            }

        }

    }

    private fun openNewScreen() {
        startActivity(Intent(this, MainActivityNew::class.java))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {

            Utils.REQUEST_CAMERA_IMAGE -> {
                if (perms.size == Utils.cameraPermissionPass.size) {
                    Log.d("myPermissionsGranted", "all Permission allow")
                    openNewScreen()
                } else {
                    Log.d("myPermissionsGranted", "not all Permission allow")
                }
            }
            else -> {
                Log.d("myPermissionsGranted", "no any  Permission allow")
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d("myPermission", "not allow")
        if (EasyPermissions.somePermissionPermanentlyDenied(this@MainActivity, perms)) {
            AppSettingsDialog.Builder(this@MainActivity).build().show()
        }
    }

}